package com.yeguohao.music;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;

import com.yeguohao.music.components.rank.Rank;
import com.yeguohao.music.components.recommend.Recommend;
import com.yeguohao.music.components.search.Search;
import com.yeguohao.music.components.singer.Singer;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Recommend.newInstance();
            case 1:
                return Singer.newInstance();
            case 2:
                return Rank.newInstance();
            default:
                return Search.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "推荐";
            case 1:
                return "歌手";
            case 2:
                return "排行";
            default:
                return "搜索";
        }
    }
}
