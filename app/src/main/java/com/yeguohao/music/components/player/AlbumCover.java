package com.yeguohao.music.components.player;

import android.os.Bundle;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.SongInfo;

import butterknife.BindView;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class AlbumCover extends BaseFragment implements SongInfo.PauseChanged {

    @BindView(R.id.album_cover)
    ImageView albumCover;

    private ViewPropertyAnimator propertyAnimator;

    @Override
    protected int layout() {
        return R.layout.fragment_album_cover;
    }

    @Override
    protected void fetch() {
        SongInfo songInfo = SongInfo.newInstance();
        songInfo.setPauseListener(this);

        String albumMid = songInfo.getAlbumMid();
        Glide.with(this).load(String.format(ALBUM_IMG_URL, albumMid)).apply(RequestOptions.circleCropTransform()).into(albumCover);

        if (!songInfo.isPause()) {
            startRotate();
        }
    }

    @Override
    public void onPauseChange(boolean isPause) {
        if (isPause) stopRotate();
        else startRotate();
    }

    private void startRotate() {
        if (propertyAnimator == null) {
            propertyAnimator = albumCover.animate().rotationBy(359).setInterpolator(new LinearInterpolator())
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
