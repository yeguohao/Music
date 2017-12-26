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
import com.yeguohao.music.common.MediaPlayerListener;
import com.yeguohao.music.common.MediaPlayerUtil;

import butterknife.BindBitmap;
import butterknife.ButterKnife;

import static com.yeguohao.music.common.MediaPlayerUtil.AT_ONCE;
import static com.yeguohao.music.common.MediaPlayerUtil.NEXT;
import static com.yeguohao.music.common.MediaPlayerUtil.PAUSE;
import static com.yeguohao.music.common.MediaPlayerUtil.PREV;
import static com.yeguohao.music.common.MediaPlayerUtil.START;
import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class MiniPlayer extends RelativeLayout implements MediaPlayerListener {

    @BindBitmap(R.drawable.player_play)
    Bitmap playBitmap;

    @BindBitmap(R.drawable.player_pause)
    Bitmap pauseBitmap;

    private ImageView play;
    private ImageView playList;
    private ImageView songThumbnail;
    private TextView songText;
    private TextView singer;

    private String songStr;
    private String singerStr;
    private String albummId;

    private MiniPlayerListener miniPlayerListener;
    private ViewPropertyAnimator propertyAnimator;

    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();

    private boolean isPause;

    public MiniPlayer(@NonNull Context context) {
        this(context, null);
    }

    public MiniPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiniPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this);
        initLayout(context);
    }

    private void initLayout(Context context) {
        inflate(context, R.layout.mini_player, this);
    }

    @Override
    public void songChanged(MediaPlayerUtil.Song song) {
        songStr = song.getSongName();
        singerStr = song.getSingerName();
        albummId = song.getAlbumMid();
        updateView();
    }

    @Override
    public void start() {
        isPause = false;
        play.setImageBitmap(pauseBitmap);
        startRotate();
        if (miniPlayerListener != null) miniPlayerListener.onPlay();
    }

    @Override
    public void pause() {
        isPause = true;
        play.setImageBitmap(playBitmap);
        stopRotate();
        if (miniPlayerListener != null) miniPlayerListener.onPause();
    }

    private void updateView() {
        if (songText != null) {
            songText.setText(songStr);
            singer.setText(singerStr);
            RequestOptions options = RequestOptions.circleCropTransform().error(R.drawable.player_play);
            Glide.with(this).load(String.format(ALBUM_IMG_URL, albummId)).apply(options).into(songThumbnail);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        play = findViewById(R.id.mini_player_play_icon);
        playList = findViewById(R.id.mini_player_playlist_icon);
        songThumbnail = findViewById(R.id.mini_player_thumbnail);
        songText = findViewById(R.id.mini_player_song);
        singer = findViewById(R.id.mini_player_singer);
        songText.setText(songStr);
        singer.setText(singerStr);

        RxView.clicks(playList).subscribe(view -> miniPlayerListener.onOpenSongList());
        RxView.clicks(songThumbnail).subscribe(view -> miniPlayerListener.onOpenPlayer());
        RxView.clicks(play).subscribe(view -> {
            if (isPause) {
                playerUtil.sent(START);
            } else {
                playerUtil.sent(PAUSE);
            }
        });
        playerUtil.on(START | PAUSE | NEXT | PREV | AT_ONCE, this);
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
