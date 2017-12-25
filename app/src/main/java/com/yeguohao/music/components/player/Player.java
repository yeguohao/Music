package com.yeguohao.music.components.player;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yeguohao.music.R;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.SongInfo;
import com.yeguohao.music.components.player.adapter.PlayerPagerAdapter;
import com.yeguohao.music.view.PlayerControl;
import com.yeguohao.music.view.PlayerProgress;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_IMG_URL;

public class Player extends BaseFragment {

    @BindView(R.id.player_background)
    ImageView background;

    @BindView(R.id.player_song)
    TextView song;

    @BindView(R.id.player_singer)
    TextView singer;

    @BindView(R.id.player_viewpager)
    ViewPager viewPager;

    @BindView(R.id.player_progress)
    PlayerProgress playerProgress;

    @BindView(R.id.player_control)
    PlayerControl playerControl;

    private SongInfo songInfo = SongInfo.newInstance();

    @Override
    protected int layout() {
        return R.layout.fragment_player;
    }

    @Override
    protected void initView(View root) {
        song.setText(songInfo.getSongName());
        singer.setText(songInfo.getSingerName());

        viewPager.setAdapter(new PlayerPagerAdapter(getChildFragmentManager()));
    }

    @Override
    protected void fetch() {
        RequestOptions options = RequestOptions.bitmapTransform(new BlurTransformation(75)).error(R.color.textColorDark).diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(String.format(ALBUM_IMG_URL, songInfo.getAlbumMid())).apply(options).into(background);
    }

    public static Player newInstance() {
        Bundle args = new Bundle();
        Player fragment = new Player();
        fragment.setArguments(args);
        return fragment;
    }
}
