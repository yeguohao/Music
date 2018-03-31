package com.yeguohao.music.player.fragments;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;

import butterknife.BindView;

public class AlbumCoverFragment extends BaseFragment{

    @BindView(R.id.album_cover) ImageView albumCover;

    private ViewPropertyAnimator propertyAnimator;
    private SongStore songStore = PlayerInstance.getSongStore();

    @Override
    protected int layout() {
        return R.layout.fragment_album_cover;
    }

    @Override
    protected void initView(View root) {
        MusicItem song = songStore.song(songStore.currentIndex());
        if (song.isPlaying()) {
            startRotate();
        }
    }

    @Override
    protected void fetch() {
        MusicItem song = songStore.song(songStore.currentIndex());
        MusicItem.Description description = song.getDescription();
        RequestOptions options = RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
        Glide.with(this).load(description.getIconUri()).apply(options).into(albumCover);
    }

    public void playBack(boolean playing) {
        if (playing) {
            startRotate();
        } else {
            stopRotate();
        }
    }

    public void switchSong() {
        fetch();
    }

    private void startRotate() {
        if (propertyAnimator == null) {
            propertyAnimator = albumCover
                    .animate()
                    .rotationBy(359)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(16000).setStartDelay(0).withEndAction(() -> {
                        propertyAnimator = null;
                        startRotate();
                    });
        }
        propertyAnimator.start();
    }

    private void stopRotate() {
        if (propertyAnimator != null) {
            propertyAnimator.cancel();
            propertyAnimator = null;
        }
    }

    public static AlbumCoverFragment newInstance() {
        return new AlbumCoverFragment();
    }

}
