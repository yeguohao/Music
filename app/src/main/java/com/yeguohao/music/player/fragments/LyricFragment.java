package com.yeguohao.music.player.fragments;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.api.RetrofitInstance;
import com.yeguohao.music.base.BaseFragment;
import com.yeguohao.music.common.TimeFormat;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.player.adapters.LyricRecyclerAdapter;
import com.yeguohao.music.player.apis.PlayerApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.util.Base64.DEFAULT;

public class LyricFragment extends BaseFragment {

    @BindView(R.id.lyirc_recycler) RecyclerView recycler;
    @BindView(R.id.lyirc_tips) TextView tips;

    private PlayerApi playerApi = RetrofitInstance.Retrofit().create(PlayerApi.class);
    private TimeFormat timeFormat = new TimeFormat();

    private LinearLayoutManager layoutManager;
    private LyricRecyclerAdapter adapter;
    private List<String> indexes = new ArrayList<>();
    private List<Long> timeStamps = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object[]> dataCache = new HashMap<>();

    private Handler handler = new Handler();
    private boolean isDrag;

    private SongStore songStore = PlayerInstance.getSongStore();

    @Override
    protected int layout() {
        return R.layout.fragment_lyric;
    }

    @Override
    protected void initView(View root) {
        adapter = new LyricRecyclerAdapter(getActivity());
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        layoutManager = (LinearLayoutManager) recycler.getLayoutManager();
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_SETTLING) {
                } else if (newState == SCROLL_STATE_IDLE) {
                    isDrag = false;
                } else if (newState == SCROLL_STATE_DRAGGING) {
                    isDrag = true;
                }
            }
        });
    }

    @Override
    protected void fetch() {
        MusicItem item = songStore.song(songStore.currentIndex());
        MusicItem.Description description = item.getDescription();
        tips.setText("正在加载");
        tips.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.INVISIBLE);
        adapter.clear();
        playerApi.getLyric(description.getSongMid())
                .map(lyric -> {
                    String result = new String(Base64.decode(lyric.getLyric(), DEFAULT));
                    int index = result.indexOf("[00");
                    return result.substring(index);
                })
                .subscribe(s -> {
                    String[] lines = s.split("\n");
                    indexes = new ArrayList<>(lines.length);
                    map = new HashMap<>(lines.length);
                    for (String line : lines) {
                        String indexStr = line.substring(1, 6);
                        String content = line.substring(10);
                        if (TextUtils.isEmpty(content)) continue;

                        String timeStamp = indexStr + ":" + line.substring(7, 9);
                        timeStamps.add(timeFormat.date2TimeStamp(timeStamp));
                        indexes.add(indexStr);
                        map.put(indexStr, content);
                    }
                    LyricFragment.this.getActivity().runOnUiThread(() -> {
                        Object[] objects = new Object[]{indexes, map};
                        dataCache.put(description.getSongMid(), objects);
                        recycler.setVisibility(View.VISIBLE);
                        tips.setVisibility(View.INVISIBLE);
                        adapter.setData(map, indexes);
                    });
                }, throwable -> {});
    }

    public void switchSong() {
        MusicItem item = songStore.song(songStore.currentIndex());
        MusicItem.Description description = item.getDescription();
        Object[] objects = dataCache.get(description.getSongMid());
        if (objects != null) {
            indexes = (List<String>) objects[0];
            map = (Map<String, String>) objects[1];
            adapter.setData(map, indexes);
        } else {
            fetch();
        }
    }

    public void progress(long currentPosition) {
        String time = timeFormat.timeStamp2Date(currentPosition);
        int position = indexes.indexOf(time);
        if (position != -1) {
            long delay = timeStamps.get(position) - currentPosition;
            if (delay < 0) delay = 200;
            handler.postDelayed(() -> {
                View secondChild = recycler.getChildAt(1);
                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                int middlePosition = firstPosition + (lastPosition - firstPosition) / 2;

                if (secondChild != null && !isDrag) {
                    int height = secondChild.getHeight();
                    int scrollSpace = 0;
                    int top = secondChild.getTop();

                    if (position < firstPosition) {
                        recycler.scrollToPosition(position);
                        scrollSpace = -(middlePosition - firstPosition + 1) * height;
                    } else if (position >= firstPosition && position < middlePosition) {
                        recycler.smoothScrollBy(0, top);
                        scrollSpace = -(middlePosition - position + 1) * height;
                    } else if (position > middlePosition && position <= lastPosition) {
                        recycler.smoothScrollBy(0, top);
                        scrollSpace = (position - middlePosition - 1) * height;
                    } else if (position > lastPosition) {
                        recycler.scrollToPosition(position);
                        scrollSpace = (lastPosition - middlePosition) * height;
                    }
                    recycler.smoothScrollBy(0, scrollSpace);
                }
                adapter.setSelectedLyric(position);
            }, delay);
        }
    }

    public static LyricFragment newInstance() {
        return new LyricFragment();
    }

}
