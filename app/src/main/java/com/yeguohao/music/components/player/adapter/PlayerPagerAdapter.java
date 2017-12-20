package com.yeguohao.music.components.player.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;

import com.yeguohao.music.components.player.AlbumCover;
import com.yeguohao.music.components.player.Lyric;
import com.yeguohao.music.components.rank.Rank;
import com.yeguohao.music.components.recommend.Recommend;
import com.yeguohao.music.components.search.Search;
import com.yeguohao.music.components.singer.Singer;

public class PlayerPagerAdapter extends FragmentPagerAdapter {

    public PlayerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return AlbumCover.newInstance();
        else return Lyric.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

}
