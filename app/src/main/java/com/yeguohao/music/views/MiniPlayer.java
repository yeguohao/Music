package com.yeguohao.music.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.yeguohao.music.R;
import com.yeguohao.music.common.XHAnimator;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.main.components.playqueue.fragments.PlaySongQueue;
import com.yeguohao.music.player.activities.PlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MiniPlayer extends RelativeLayout {

    @BindView(R.id.mini_player_play_icon) ImageView play;
    @BindView(R.id.mini_player_playlist_icon) ImageView playList;
    @BindView(R.id.mini_player_thumbnail) ImageView songThumbnail;
    @BindView(R.id.mini_player_song) TextView songText;
    @BindView(R.id.mini_player_singer) TextView singer;

    private ViewPropertyAnimator propertyAnimator;
    private PlaySongQueue songQueue;
    private XHAnimator xhAnimator;

    public MiniPlayer(@NonNull Context context) {
        this(context, null);
    }

    public MiniPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiniPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
        ButterKnife.bind(this);
        xhAnimator = XHAnimator.instance(songThumbnail);
    }

    private void initLayout(Context context) {
        inflate(context, R.layout.mini_player, this);
    }

    public void resume() {
        SongStore songStore = PlayerInstance.getSongStore();
        if (songStore.songs().isEmpty()) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        updateView();

        PlayerInstance.getUpdater().setCompletedCallback((this::completed));
    }

    private void completed() {
        updateView();
        if (songQueue != null && songQueue.isVisible()) {
            songQueue.completed();
        }
    }

    public void pause() {
        xhAnimator.stop();
        PlayerInstance.getUpdater().setCompletedCallback(null);
    }

    private void updateView() {
        SongStore songStore = PlayerInstance.getSongStore();

        MusicItem song = songStore.song(songStore.currentIndex());
        MusicItem.Description description = song.getDescription();
        songText.setText(description.getSongName());
        singer.setText(description.getSingerName());

        play.setSelected(song.isPlaying());
        if (song.isPlaying()) {
            xhAnimator.start();
        } else {
            xhAnimator.stop();
        }

        RequestOptions options = RequestOptions.circleCropTransform().error(R.drawable.player_play);
        Glide.with(this).load(description.getIconUri()).apply(options).into(songThumbnail);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RxView.clicks(playList).subscribe(view -> openSongList());
        RxView.clicks(songThumbnail).subscribe(view -> PlayerActivity.startActivity((Activity) getContext(), null, 0));
        RxView.clicks(play).subscribe(view -> {
            SongStore songStore = PlayerInstance.getSongStore();
            MusicController musicController = PlayerInstance.getMusicController();
            MusicItem song = songStore.song(songStore.currentIndex());

            if (song.isPlaying()) {
                musicController.pause();
                xhAnimator.stop();
            } else {
                musicController.play();
                xhAnimator.start();
            }
            play.setSelected(song.isPlaying());
        });
    }

    private void openSongList() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        songQueue = PlaySongQueue.newInstance();
        songQueue.setMiniPlayer(this);
        songQueue.show(activity.getFragmentManager(), "");
    }

    public void switchSong() {
        updateView();
    }

}
