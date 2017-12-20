package com.yeguohao.music.components.player;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.SongInfo;

public class Lyric extends BaseFragment {

    private RecyclerView recycler;

    private Instance instance = new Instance();
    private SongInfo songInfo = SongInfo.newInstance();
    
    @Override
    protected int layout() {
        return R.layout.fragment_lyric;
    }

    @Override
    protected void initView(View root) {
        recycler = (RecyclerView) root;

    }

    @Override
    protected void fetch() {
        instance.Player().getLyric(songInfo.getSongMid());
    }

    public static Lyric newInstance() {
        
        Bundle args = new Bundle();
        
        Lyric fragment = new Lyric();
        fragment.setArguments(args);
        return fragment;
    }
}
