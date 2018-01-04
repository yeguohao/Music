package com.yeguohao.music.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.yeguohao.music.R;
import com.yeguohao.music.common.MediaPlayerUtil;
import com.yeguohao.music.components.player.Song;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class MiniPlayer extends RelativeLayout {

    @BindBitmap(R.drawable.player_play)
    Bitmap playBitmap;

    @BindBitmap(R.drawable.player_pause)
    Bitmap pauseBitmap;

    @BindView(R.id.mini_player_play_icon)
    ImageView play;

    @BindView(R.id.mini_player_playlist_icon)
    ImageView playList;

    @BindView(R.id.mini_player_thumbnail)
    ImageView songThumbnail;

    @BindView(R.id.mini_player_song)
    TextView songText;

    @BindView(R.id.mini_player_singer)
    TextView singer;

    private String songStr;
    private String singerStr;
    private String albumMId;

    private MiniPlayerListener miniPlayerListener;
    private ViewPropertyAnimator propertyAnimator;

    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();

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
    }

    private void initLayout(Context context) {
        inflate(context, R.layout.mini_player, this);
    }

    public void resume() {
        Song song = playerUtil.getCurrentSong();
        if (song == null) {
            play.setClickable(false);
            playList.setClickable(false);
            return;
        } else {
            play.setClickable(true);
            playList.setClickable(true);
        }
        songStr = song.getSongName();
        singerStr = song.getSingerName();
        albumMId = song.getAlbumMid();
        updateView();
    }

    public void pause() {
        stopRotate();
    }

    private void updateView() {
        songText.setText(songStr);
        singer.setText(singerStr);
        if (playerUtil.isPlay()) {
            play.setImageBitmap(pauseBitmap);
            startRotate();
            if (miniPlayerListener != null) miniPlayerListener.onPlay();
        } else {
            play.setImageBitmap(playBitmap);
            stopRotate();
            if (miniPlayerListener != null) miniPlayerListener.onPause();
        }

        RequestOptions options = RequestOptions.circleCropTransform().error(R.drawable.player_play);
        Glide.with(this).load(String.format(ALBUM_IMG_URL, albumMId)).apply(options).into(songThumbnail);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        songText.setText(songStr);
        singer.setText(singerStr);

        RxView.clicks(playList).subscribe(view -> {
            if (miniPlayerListener != null) miniPlayerListener.onOpenSongList();
        });
        RxView.clicks(songThumbnail).subscribe(view -> {
            if (playerUtil.getCurrentSong() != null && miniPlayerListener != null) {
                miniPlayerListener.onOpenPlayer();
            }
        });
        RxView.clicks(play).subscribe(view -> {
            if (playerUtil.isPlay()) {
                play.setImageBitmap(playBitmap);
                playerUtil.pause();
                stopRotate();
            } else {
                play.setImageBitmap(pauseBitmap);
                playerUtil.start();
                startRotate();
            }
        });
    }

    private void startRotate() {
        if (propertyAnimator == null) {
            propertyAnimator = songThumbnail
                    .animate()
                    .rotationBy(359)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(6000).setStartDelay(0).withEndAction(() -> {
                        propertyAnimator = null;
                        startRotate();
                    });
        }
    }

    private void stopRotate() {
        if (propertyAnimator != null) {
            propertyAnimator.cancel();
            propertyAnimator = null;
        }
    }

    public void setMiniPlayerListener(MiniPlayerListener miniPlayerListener) {
        this.miniPlayerListener = miniPlayerListener;
    }

    public interface MiniPlayerListener {

        void onPlay();

        void onPause();

        void onOpenSongList();

        void onOpenPlayer();
    }
}
