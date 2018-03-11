package com.yeguohao.music.main.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;

import com.yeguohao.music.main.components.rank.fragments.RankFragment;
import com.yeguohao.music.main.components.recommend.fragments.RecommendFragment;
import com.yeguohao.music.main.components.search.fragments.SearchFragment;
import com.yeguohao.music.main.components.singer.fragments.SingerFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RecommendFragment.newInstance();
            case 1:
                return SingerFragment.newInstance();
            case 2:
                return RankFragment.newInstance();
            default:
                return SearchFragment.newInstance();
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
