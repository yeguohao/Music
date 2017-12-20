package com.yeguohao.music.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import com.yeguohao.music.common.SongInfo;

import butterknife.BindBitmap;
import butterknife.ButterKnife;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class MiniPlayer extends RelativeLayout {

    @BindBitmap(R.drawable.player_play)
    Bitmap playBitmap;

    @BindBitmap(R.drawable.player_pause)
    Bitmap pauseBitmap;

    private ImageView play;
    private ImageView playList;
    private ImageView songThumbnail;
    private TextView song;
    private TextView singer;

    private String songStr;
    private String singerStr;
    private String albummId;

    private SongInfo songInfo;

    private MiniPlayerListener miniPlayerListener;

    private ViewPropertyAnimator propertyAnimator;

    public MiniPlayer(@NonNull Context context) {
        this(context, null);
    }

    public MiniPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiniPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this);

        songInfo = SongInfo.newInstance();
        initLayout(context);
        restore();
    }

    private void initLayout(Context context) {
        inflate(context, R.layout.mini_player, this);
    }

    private void restore() {
        songStr = songInfo.getSongName();
        singerStr = songInfo.getSingerName();
        albummId = songInfo.getAlbumMid();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        play = findViewById(R.id.mini_player_play_icon);
        playList = findViewById(R.id.mini_player_playlist_icon);
        songThumbnail = findViewById(R.id.mini_player_thumbnail);
        if (songInfo.isPause()) {
            pause();
        } else {
            play();
        }

        song = findViewById(R.id.mini_player_song);
        singer = findViewById(R.id.mini_player_singer);

        song.setText(songStr);
        singer.setText(singerStr);

        RequestOptions options = RequestOptions.circleCropTransform()
                .error(R.drawable.player_play);
        Glide.with(this).load(String.format(ALBUM_IMG_URL, albummId)).apply(options).into(songThumbnail);

        RxView.clicks(play).subscribe(view -> {
            if (songInfo.isPause()) {
                play();
                miniPlayerListener.onPlay();
            } else {
                pause();
                miniPlayerListener.onPause();
            }
            songInfo.setPause(!songInfo.isPause());
        });
        RxView.clicks(playList).subscribe(view -> miniPlayerListener.onOpenSongList());
        RxView.clicks(songThumbnail).subscribe(view -> miniPlayerListener.onOpenPlayer());
    }

    private void play() {
        play.setImageBitmap(pauseBitmap);
        startRotate();
    }

    private void pause() {
        play.setImageBitmap(playBitmap);
        stopRotate();
    }

    private void startRotate() {
        if (propertyAnimator == null) {
            propertyAnimator = songThumbnail.animate().rotationBy(359).setInterpolator(new LinearInterpolator())
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
