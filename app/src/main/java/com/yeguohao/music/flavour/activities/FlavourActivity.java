package com.yeguohao.music.flavour.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yeguohao.music.R;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.flavour.adapters.FlavourAndRecentlyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yeguohao.music.common.Util.randomRange;
import static com.yeguohao.music.player.PlayerConstance.RANDOM;

public class FlavourActivity extends AppCompatActivity {

    @BindView(R.id.flavour_recycler) RecyclerView recycler;
    @BindView(R.id.title_flavour) TextView flavour;
    @BindView(R.id.title_recently) TextView recently;

    private List<MusicItem> recentlyData;
    private List<MusicItem> flavourData;
    private FlavourAndRecentlyAdapter flavourAdapter;

    private SongStore songStore = PlayerInstance.getSongStore();

    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour);
        ButterKnife.bind(this);

        flavour.setSelected(true);
        currentView = flavour;

        flavourAdapter = new FlavourAndRecentlyAdapter(R.layout.item_song);
        recycler.setHasFixedSize(true);
        flavourAdapter.bindToRecyclerView(recycler);

        recentlyData = songStore.recentlySongs();
        flavourData = songStore.flavourSongs();
        showUiByFlavourData();
        click();
    }

    private void click() {
        flavourAdapter.setOnItemClickListener((adapter, view, position) -> {
            MusicController musicController = PlayerInstance.getMusicController();

            MusicItem item = flavourAdapter.getData().get(position);
            int index = songStore.songs().indexOf(item);
            boolean isSet = index != -1;
            if (item.isPlaying()) {
                return;
            }
            if (!isSet) {
                songStore.songs().add(item);
                index = songStore.songs().size() - 1;
            }
            musicController.switchSong(index);
            musicController.play();
        });
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
        List<MusicItem> data;
        if (currentView == flavour) {
            data = flavourData;
        } else {
            data = recentlyData;
        }

        if (data.isEmpty()) {
            Toast.makeText(this, "不能播放", Toast.LENGTH_LONG).show();
            return;
        }

        MusicItem song = songStore.song(songStore.currentIndex());
        MusicController musicController = PlayerInstance.getMusicController();
        musicController.replaceData(data);
        switchMode2Random(musicController);

        int index = data.indexOf(song);
        if (index == -1) {
            index = randomRange(0, data.size() - 1);
        }
        musicController.switchSong(index);
        musicController.play();

        String songName = songStore.song(songStore.currentIndex()).getDescription().getSongName();
        Toast.makeText(this, "正在播放: " + songName, Toast.LENGTH_SHORT).show();
    }

    private void switchMode2Random(MusicController musicController) {
        while (musicController.getMode() != RANDOM) {
            musicController.switchMode();
        }
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
