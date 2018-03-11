package com.yeguohao.music.player.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.yeguohao.music.player.fragments.AlbumCoverFragment;
import com.yeguohao.music.player.fragments.LyricFragment;

public class PlayerPagerAdapter extends FragmentPagerAdapter {

    public PlayerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return AlbumCoverFragment.newInstance();
        else return LyricFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

}
