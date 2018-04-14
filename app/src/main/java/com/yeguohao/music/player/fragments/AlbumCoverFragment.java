package com.yeguohao.music.player.fragments;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.XHAnimator;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;

import butterknife.BindView;

public class AlbumCoverFragment extends BaseFragment{

    @BindView(R.id.album_cover) ImageView albumCover;

    private ViewPropertyAnimator propertyAnimator;
    private SongStore songStore = PlayerInstance.getSongStore();
    private XHAnimator xhAnimator;

    @Override
    protected int layout() {
        return R.layout.fragment_album_cover;
    }

    @Override
    protected void initView(View root) {
        xhAnimator = XHAnimator.instance(albumCover);
        MusicItem song = songStore.song(songStore.currentIndex());
        if (song.isPlaying()) {
            xhAnimator.start();
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
            xhAnimator.start();
        } else {
            xhAnimator.stop();
        }
    }

    public void switchSong() {
        fetch();
    }

    public static AlbumCoverFragment newInstance() {
        return new AlbumCoverFragment();
    }

}
