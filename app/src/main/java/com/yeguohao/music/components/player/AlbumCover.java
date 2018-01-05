package com.yeguohao.music.components.player;

import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.MediaPlayerUtil;

import butterknife.BindView;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class AlbumCover extends BaseFragment implements MediaPlayerUtil.OnStateListener{

    @BindView(R.id.album_cover)
    ImageView albumCover;

    private ViewPropertyAnimator propertyAnimator;
    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();

    @Override
    protected int layout() {
        return R.layout.fragment_album_cover;
    }

    @Override
    protected void initView(View root) {
        playerUtil.setStateListener(this);
        if (playerUtil.isPlay()) {
            startRotate();
        }
    }

    @Override
    protected void fetch() {
        Song song = playerUtil.getCurrentSong();
        RequestOptions options = RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
        Glide.with(this).load(String.format(ALBUM_IMG_URL, song.getAlbumMid())).apply(options).into(albumCover);
    }

    @Override
    public void onSwitchSong(Song newSong) {
        fetch();
    }

    @Override
    public void onPlayStart() {
        startRotate();
    }

    @Override
    public void onPlayPause() {
        stopRotate();
    }

    private void startRotate() {
        if (propertyAnimator == null) {
            propertyAnimator = albumCover
                    .animate()
                    .rotationBy(359)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(8000).setStartDelay(0).withEndAction(() -> {
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

    public static AlbumCover newInstance() {
        Bundle args = new Bundle();
        AlbumCover fragment = new AlbumCover();
        fragment.setArguments(args);
        return fragment;
    }

}
