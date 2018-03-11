package com.yeguohao.music.player.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.player.MediaPlayerUtil;
import com.yeguohao.music.common.ProgressPercent;
import com.yeguohao.music.common.TimeFormat;
import com.yeguohao.music.player.entities.Song;
import com.yeguohao.music.player.adapters.PlayerPagerAdapter;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.yeguohao.music.player.PlayerConstance.ALBUM_IMG_URL;
import static com.yeguohao.music.player.PlayerConstance.LOOP;
import static com.yeguohao.music.player.PlayerConstance.SEQUENCE;

public class PlayerFragments extends BaseFragment implements MediaPlayerUtil.OnStateListener {

    @BindView(R.id.player_background)
    ImageView background;

    @BindView(R.id.player_song)
    TextView songText;

    @BindView(R.id.player_singer)
    TextView singer;

    @BindView(R.id.player_viewpager)
    ViewPager viewPager;

    @BindBitmap(R.drawable.player_play)
    Bitmap playBitmap;

    @BindBitmap(R.drawable.player_pause)
    Bitmap pauseBitmap;

    @BindBitmap(R.drawable.player_favorite)
    Bitmap favoriteBitamp;

    @BindBitmap(R.drawable.player_favorite_border)
    Bitmap favoriteBorderBitmap;

    @BindBitmap(R.drawable.sequence)
    Bitmap sequenceBitmap;

    @BindBitmap(R.drawable.loop)
    Bitmap loopBitmap;

    @BindBitmap(R.drawable.random)
    Bitmap randomBitmap;

    @BindView(R.id.player_mode)
    ImageView modeImageView;

    @BindView(R.id.player_prev)
    ImageView prev;

    @BindView(R.id.player_play)
    ImageView play;

    @BindView(R.id.player_next)
    ImageView next;

    @BindView(R.id.player_favorite)
    ImageView favoriteImageView;

    @BindView(R.id.player_current_time)
    TextView currentTime;

    @BindView(R.id.player_total_time)
    TextView totalTime;

    @BindView(R.id.player_progress)
    SeekBar progress;

    private static final String TAG = "PlayerApi";

    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();
    private TimeFormat timeFormat = new TimeFormat();
    private ProgressPercent progressPercent = new ProgressPercent();

    private boolean seekUser;

    @Override
    protected int layout() {
        return R.layout.fragment_player;
    }

    @Override
    protected void initView(View root) {
        Song songInfo = playerUtil.getCurrentSong();
        songText.setText(songInfo.getSongName());
        singer.setText(songInfo.getSingerName());
        totalTime.setText(timeFormat.timeStamp2Date(songInfo.getDuration()));

        progress.setProgress(progressPercent.percent(playerUtil.getCurTime(), songInfo.getDuration()));
        setPlayBitmapByPlayState();
        setTextByPlayProgress();
        setModeBitmapByMode();
        setFavoriteBitmapByFavorite();
        setBackgroundImage(songInfo.getAlbumMid());

        playerUtil.setStateListener(this);
        viewPager.setAdapter(new PlayerPagerAdapter(getChildFragmentManager()));
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int seekPosition;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    double total = playerUtil.getDuration();
                    double fen = progress / 100d;
                    seekPosition = (int) (total * fen);
                    currentTime.setText(timeFormat.timeStamp2Date(seekPosition));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekUser = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekUser = false;
                playerUtil.seekTo(seekPosition);
            }
        });
    }

    @OnClick({R.id.player_mode, R.id.player_prev, R.id.player_play, R.id.player_next, R.id.player_favorite})
    void controlClicks(View view) {
        switch (view.getId()) {
            case R.id.player_mode: {
                playerUtil.toggleMode();
                setModeBitmapByMode();
                break;
            }
            case R.id.player_prev: {
                playerUtil.prevSong();
                break;
            }
            case R.id.player_play: {
                if (playerUtil.isPlay()) {
                    play.setImageBitmap(playBitmap);
                    playerUtil.pause();
                } else {
                    play.setImageBitmap(pauseBitmap);
                    playerUtil.start();
                }
                break;
            }
            case R.id.player_next: {
                playerUtil.nextSong();
                break;
            }
            case R.id.player_favorite: {
                if (playerUtil.isFavorite()) {
                    playerUtil.setFavorite(false);
                } else {
                    playerUtil.setFavorite(true);
                }
                setFavoriteBitmapByFavorite();
                break;
            }
        }
    }

    private void loopSong() {
        progress.setProgress(0);
        currentTime.setText(R.string.initial_time);
        playerUtil.pause();
        playerUtil.seekTo(0);
        playerUtil.start();
    }

    // 切换歌曲之后界面需要作出相应改变
    private void switchSong(Song song) {
        progress.setProgress(0);
        setFavoriteBitmapByFavorite();
        setBackgroundImage(song.getAlbumMid());

        play.setImageBitmap(pauseBitmap);
        currentTime.setText(R.string.initial_time);
        totalTime.setText(R.string.initial_time);
        songText.setText(song.getSongName());
        singer.setText(song.getSingerName());
    }

    private void setModeBitmapByMode() {
        Bitmap bitmap;
        int mode = playerUtil.getMode();
        if (mode == SEQUENCE) {
            bitmap = sequenceBitmap;
        } else if (mode == LOOP) {
            bitmap = loopBitmap;
        } else {
            bitmap = randomBitmap;
        }
        modeImageView.setImageBitmap(bitmap);
    }

    private void setFavoriteBitmapByFavorite() {
        if (playerUtil.isFavorite()) {
            favoriteImageView.setImageBitmap(favoriteBitamp);
        } else {
            favoriteImageView.setImageBitmap(favoriteBorderBitmap);
        }
    }

    private void setTextByPlayProgress() {
        currentTime.setText(timeFormat.timeStamp2Date(playerUtil.getCurTime()));
    }

    private void setPlayBitmapByPlayState() {
        if (playerUtil.isPlay() || playerUtil.getState() == MediaPlayerUtil.LOADING) {
            play.setImageBitmap(pauseBitmap);
        } else {
            play.setImageBitmap(playBitmap);
        }
    }

    private void setBackgroundImage(String albumMid) {
        RequestOptions options = RequestOptions.bitmapTransform(new BlurTransformation(200)).error(R.color.textColorDark).diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(String.format(ALBUM_IMG_URL, albumMid)).apply(options).into(background);
    }

    public static PlayerFragments newInstance() {
        Bundle args = new Bundle();
        PlayerFragments fragment = new PlayerFragments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLoaded() {
        totalTime.setText(timeFormat.timeStamp2Date(playerUtil.getDuration()));
    }

    @Override
    public void onPlayProgress(int percent, int currentPosition) {
        if (!seekUser) {
            progress.setProgress(percent);
            currentTime.setText(timeFormat.timeStamp2Date(currentPosition));
        }
    }

    @Override
    public void onSwitchSong(Song newSong) {
        switchSong(newSong);
    }

    @Override
    public void onPlayFailed() {
        Toast.makeText(getActivity(), "播放错误", Toast.LENGTH_SHORT).show();
        play.setImageBitmap(playBitmap);
    }
}
