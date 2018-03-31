package com.yeguohao.music.flavour.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.flavour.adapters.FlavourAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlavourActivity extends AppCompatActivity {

    @BindView(R.id.flavour_recycler) RecyclerView recycler;
    @BindView(R.id.title_flavour) TextView flavour;
    @BindView(R.id.title_recently) TextView recently;

    private List<MusicItem> recentlyData;
    private List<MusicItem> flavourData;
    private FlavourAdapter flavourAdapter;

    private SongStore songStore = PlayerInstance.getSongStore();

    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour);
        ButterKnife.bind(this);

        flavour.setSelected(true);
        currentView = flavour;
        flavourAdapter = new FlavourAdapter(R.layout.item_song);
        recycler.setHasFixedSize(true);
        flavourAdapter.bindToRecyclerView(recycler);

        recentlyData = songStore.recentlySongs();
        flavourData = songStore.flavourSongs();
        showUiByFlavourData();
    }

    @OnClick({R.id.title_flavour, R.id.title_recently})
    void choose(View view) {
        if (view.isSelected()) {
            return;
        }
        if (currentView != null) {
            currentView.setSelected(false);
            currentView = view;
        }
        view.setSelected(true);
        if (view.getId() == R.id.title_flavour) {
            showUiByFlavourData();
        } else {
            showUiByRecentlyData();
        }
    }

    @OnClick(R.id.random_play)
    void randomPlay() {

    }

    private void showUiByRecentlyData() {
        flavourAdapter.replaceData(recentlyData);
        if (recentlyData.isEmpty()) {
            flavourAdapter.setEmptyView(R.layout.recently_empty);
        }
    }

    private void showUiByFlavourData() {
        flavourAdapter.replaceData(flavourData);
        if (flavourData.isEmpty()) {
            flavourAdapter.setEmptyView(R.layout.flavour_empty);
        }
    }

}
