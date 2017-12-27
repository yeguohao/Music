package com.yeguohao.music.components.player;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.widget.RxSeekBar;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.MediaPlayerUtil;
import com.yeguohao.music.components.player.adapter.PlayerPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class Player extends BaseFragment {

    private static final String TAG = "Player";
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
    Bitmap favoriteBorderBitamp;

    @BindView(R.id.player_mode)
    ImageView mode;

    @BindView(R.id.player_prev)
    ImageView prev;

    @BindView(R.id.player_play)
    ImageView play;

    @BindView(R.id.player_next)
    ImageView next;

    @BindView(R.id.player_favorite)
    ImageView favorite;

    @BindView(R.id.player_current_time)
    TextView curTime;

    @BindView(R.id.player_total_time)
    TextView totalTime;

    @BindView(R.id.player_progress)
    SeekBar progress;

    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();

    @Override
    protected int layout() {
        return R.layout.fragment_player;
    }

    @Override
    protected void initView(View root) {
        Song songInfo = playerUtil.getCurrentSong();
        songText.setText(songInfo.getSongName());
        singer.setText(songInfo.getSingerName());
        if (playerUtil.isPlay() || playerUtil.getState() == MediaPlayerUtil.LOADING) {
            play.setImageBitmap(pauseBitmap);
        } else {
            play.setImageBitmap(playBitmap);
        }
        progress.setMax(100);
        RxSeekBar.changes(progress).subscribe(integer -> {
            if (integer == 100) {
                switchSong(playerUtil.next());
            } else {
                playerUtil.seekTo(integer);
            }
        });
        playerUtil.setProgressListener((curPos, duration) -> {
            int percent = curPos / duration * 100;
            Log.e(TAG, "percent: " + percent);
            progress.setProgress(percent);
            curTime.setText(format(curPos));
            totalTime.setText(format(duration));
        });
        viewPager.setAdapter(new PlayerPagerAdapter(getChildFragmentManager()));
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    private String format(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    protected void fetch() {
        Song songInfo = playerUtil.getCurrentSong();
        RequestOptions options = RequestOptions.bitmapTransform(new BlurTransformation(200)).error(R.color.textColorDark).diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(String.format(ALBUM_IMG_URL, songInfo.getAlbumMid())).apply(options).into(background);

    }

    @OnClick({R.id.player_mode, R.id.player_prev, R.id.player_play, R.id.player_next, R.id.player_favorite})
    void controlClicks(View view) {
        switch (view.getId()) {
            case R.id.player_mode: {

                break;
            }
            case R.id.player_prev: {
                switchSong(playerUtil.prev());
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
                switchSong(playerUtil.next());
                break;
            }
            case R.id.player_favorite: {
                if (playerUtil.isFavorite()) {
                    favorite.setImageBitmap(favoriteBorderBitamp);
                    playerUtil.setFavorite(false);
                } else {
                    favorite.setImageBitmap(favoriteBitamp);
                    playerUtil.setFavorite(true);
                }
                break;
            }
        }
    }

    // 切换歌曲之后界面需要作出相应改变
    private void switchSong(Song song) {
        progress.setProgress(0);
        if (playerUtil.isFavorite()) {
            favorite.setImageBitmap(favoriteBitamp);
        } else {
            favorite.setImageBitmap(favoriteBorderBitamp);
        }

        songText.setText(song.getSongName());
        singer.setText(song.getSingerName());
    }

    public static Player newInstance() {
        Bundle args = new Bundle();
        Player fragment = new Player();
        fragment.setArguments(args);
        return fragment;
    }
}
