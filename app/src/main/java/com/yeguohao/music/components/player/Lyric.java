package com.yeguohao.music.components.player;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.api.Instance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.MediaPlayerListener;
import com.yeguohao.music.common.MediaPlayerUtil;
import com.yeguohao.music.components.player.adapter.LyricRecyclerAdapter;

import butterknife.BindView;

public class Lyric extends BaseFragment implements MediaPlayerListener {

    private static final String TAG = "Lyric";

    @BindView(R.id.lyirc_recycler)
    RecyclerView recycler;

    @BindView(R.id.lyirc_tips)
    TextView tips;

    private Instance instance = new Instance();
    private MediaPlayerUtil playerUtil = MediaPlayerUtil.getPlayerUtil();

    private LyricRecyclerAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.fragment_lyric;
    }

    @Override
    protected void initView(View root) {
        adapter = new LyricRecyclerAdapter();
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {

        });
    }

    @Override
    protected void fetch() {
    }

    @Override
    public void songChanged(Song song) {
        tips.setText("正在加载");
        tips.setVisibility(View.VISIBLE);
        adapter.clear();
        instance.Player().getLyric(song.getSongMid())
                .map(lyric -> {
                    Log.e(TAG, "songChanged: " + lyric.getLyric());
                    return lyric;
                })
                .subscribe();
    }

    public static Lyric newInstance() {
        Bundle args = new Bundle();
        Lyric fragment = new Lyric();
        fragment.setArguments(args);
        return fragment;
    }
}
