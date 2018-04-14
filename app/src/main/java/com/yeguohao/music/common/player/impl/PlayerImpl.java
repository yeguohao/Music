package com.yeguohao.music.common.player.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.callback.OnBufferedPositionCallback;
import com.yeguohao.music.common.player.callback.OnCompletedCallback;
import com.yeguohao.music.common.player.callback.OnPositionCallback;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.common.player.interfaces.Player;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.exoplayer2.Player.STATE_ENDED;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class PlayerImpl implements Player {

    private SongStore songStore;
    private MusicItem item;
    private PositionUpdater updater;
    private Context context;

    private SimpleExoPlayer simpleExoPlayer;

    public PlayerImpl(SongStore songStore, Context context) {
        this.context = context;
        this.songStore = songStore;
        this.simpleExoPlayer = createPlayer();
        this.updater = new PositionUpdater();
    }

    private SimpleExoPlayer createPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        return ExoPlayerFactory.newSimpleInstance(context, trackSelector);
    }

    private MediaSource createMediaSource(String path) {
        Uri uri = Uri.parse(path);
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, "user-agent", bandwidthMeter);
        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private void loadNI() {
        reset();
        item = songStore.songs().get(songStore.currentIndex());
        MusicItem.Description description = item.getDescription();

        MediaSource mediaSource = createMediaSource(description.getStreamUri());
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

        songStore.addRecentlySong(item);
    }

    private void reset() {
        if (item == null) {
            return;
        }
        item.setPlaying(false);
        item.setCurrentPosition(0);
        item.setBufferedPosition(0);
        item.setDuration(0);
    }

    @Override
    public void play() {
        MusicItem item = songStore.songs().get(songStore.currentIndex());
        if (item == this.item) {
            simpleExoPlayer.setPlayWhenReady(true);
        } else {
            loadNI();
        }
        updater.schedule();
        item.setPlaying(true);
    }

    @Override
    public void pause() {
        simpleExoPlayer.setPlayWhenReady(false);
        updater.cancel();
        item.setPlaying(false);
    }

    @Override
    public void seek(long position) {
        simpleExoPlayer.seekTo(position);
        simpleExoPlayer.setPlayWhenReady(true);
        item.setCurrentPosition(position);
    }

    public PositionUpdater getUpdater() {
        return updater;
    }

    public class PositionUpdater {

        private Timer timer = new Timer(true);
        private OnPositionCallback positionCallback;
        private OnBufferedPositionCallback bufferedPositionCallback;
        private OnCompletedCallback completedCallback;

        private Handler handler = new Handler();

        private int period = 1000;

        PositionUpdater() {
            simpleExoPlayer.addListener(new com.google.android.exoplayer2.Player.DefaultEventListener() {
                @Override
                public void onLoadingChanged(boolean isLoading) {
                    if (!isLoading) {
                        item.setDuration(simpleExoPlayer.getDuration());
                        update();
                    }
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    playerStateChanged(playbackState);
                }
            });
        }

        private void playerStateChanged(int playbackState) {
            if (playbackState == STATE_ENDED) {
                pause();
                handler.postDelayed(() -> {
                    MusicController musicController = PlayerInstance.getMusicController();
                    musicController.next();
                    if (completedCallback != null) {
                        completedCallback.completed();
                    }
                }, 1000);
            } else if (playbackState == STATE_READY) {
                update();
            }
        }

        public synchronized void setPositionCallback(OnPositionCallback positionCallback) {
            this.positionCallback = positionCallback;
        }

        public synchronized void setBufferedPositionCallback(OnBufferedPositionCallback bufferedPositionCallback) {
            this.bufferedPositionCallback = bufferedPositionCallback;
        }

        public synchronized void setCompletedCallback(OnCompletedCallback completedCallback) {
            this.completedCallback = completedCallback;
        }

        private void update() {
            long duration = simpleExoPlayer.getDuration();
            if (duration == C.TIME_UNSET) {
                return;
            }

            long currentPosition = simpleExoPlayer.getCurrentPosition();
            if (currentPosition >= duration) {
                currentPosition = duration;
            }

            item.setCurrentPosition(currentPosition);
            if (positionCallback != null) {
                positionCallback.update(currentPosition);
            }

            // 缓存增加时才通知
            long bufferedPosition = simpleExoPlayer.getBufferedPosition();
            if (item.getBufferedPosition() != bufferedPosition) {
                item.setBufferedPosition(bufferedPosition);
                if (bufferedPositionCallback != null) {
                    bufferedPositionCallback.update(bufferedPosition);
                }
            }
        }

        private void schedule() {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    update();
                }
            }, 0, period);
        }

        private void cancel() {
            timer.cancel();
            timer = new Timer(true);
        }

    }

}
