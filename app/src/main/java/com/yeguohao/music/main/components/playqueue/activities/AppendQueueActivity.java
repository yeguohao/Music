package com.yeguohao.music.main.components.playqueue.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yeguohao.music.R;
import com.yeguohao.music.adapters.SearchHistoryAdapter;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.flavour.adapters.FlavourAndRecentlyAdapter;
import com.yeguohao.music.views.SearchHistory;
import com.yeguohao.music.views.SearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppendQueueActivity extends AppCompatActivity {

    @BindView(R.id.title_recently) TextView recently;
    @BindView(R.id.recycler_recently) RecyclerView recentlyRecycler;
    @BindView(R.id.recycler_search_history) RecyclerView searchHistoryRecycler;
    @BindView(R.id.search) SearchView search;

    private SearchHistory.SearchRecord record = SearchHistory.SearchRecord.getSearchRecord();
    private SongStore songStore = PlayerInstance.getSongStore();

    private SearchHistoryAdapter searchHistoryAdapter;
    private FlavourAndRecentlyAdapter recentlyAdapter;

    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_append_queue);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        recently.setSelected(true);
        currentView = recently;

        searchHistoryAdapter = new SearchHistoryAdapter(R.layout.item_search_history, record);
        recentlyAdapter = new FlavourAndRecentlyAdapter(R.layout.item_recently);
        recentlyRecycler.setAdapter(recentlyAdapter);
        searchHistoryRecycler.setAdapter(searchHistoryAdapter);

        recentlyAdapter.replaceData(songStore.recentlySongs());
        searchHistoryAdapter.setListener(search::setText);
        recentlyAdapter.setListener(this::addToQueue);
        search.setListener(this::addToQueue);
    }

    private void addToQueue(MusicItem item) {
        MusicController musicController = PlayerInstance.getMusicController();
        List<MusicItem> songs = songStore.songs();
        if (songs.contains(item)) {
            songs.remove(item);
        }
        songs.add(0, item);
        musicController.switchSong(0);
        musicController.play();

        Toast.makeText(this, "已添加歌曲到播放队列", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.title_recently, R.id.title_search})
    void titleClick(View view) {
        if (view.isSelected()) {
            return;
        }
        if (currentView != null) {
            currentView.setSelected(false);
            currentView = view;
        }
        view.setSelected(true);
        if (view.getId() == R.id.title_recently) {
            showRecentlyData();
        } else {
            showSearchHistoryData();
        }
    }

    private void showRecentlyData() {
        searchHistoryRecycler.setVisibility(View.INVISIBLE);
        recentlyRecycler.setVisibility(View.VISIBLE);
        recentlyAdapter.replaceData(songStore.recentlySongs());
    }

    private void showSearchHistoryData() {
        searchHistoryRecycler.setVisibility(View.VISIBLE);
        recentlyRecycler.setVisibility(View.INVISIBLE);
        searchHistoryAdapter.replaceData(record.searchKeys);
    }

}
