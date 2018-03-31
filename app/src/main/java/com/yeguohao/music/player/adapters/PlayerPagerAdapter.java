package com.yeguohao.music.player.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.yeguohao.music.player.fragments.AlbumCoverFragment;
import com.yeguohao.music.player.fragments.LyricFragment;

public class PlayerPagerAdapter extends FragmentPagerAdapter {

    private AlbumCoverFragment albumCoverFragment;
    private LyricFragment lyricFragment;

    public PlayerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            albumCoverFragment = AlbumCoverFragment.newInstance();
            return albumCoverFragment;
        } else {
            lyricFragment = LyricFragment.newInstance();
            return lyricFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public AlbumCoverFragment getAlbumCoverFragment() {
        return albumCoverFragment;
    }

    public LyricFragment getLyricFragment() {
        return lyricFragment;
    }

}
