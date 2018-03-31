package com.yeguohao.music.player.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yeguohao.music.R;
import com.yeguohao.music.base.NotStatusBarActivity;
import com.yeguohao.music.common.player.PlayerInstance;
import com.yeguohao.music.common.player.impl.MusicItem;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.player.fragments.PlayerFragment;

import java.util.List;

public class PlayerActivity extends NotStatusBarActivity {

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, List<MusicItem> musicItems, int position) {
        if (musicItems != null) {
            handle(musicItems, position);
        }
        Intent intent = new Intent(activity, PlayerActivity.class);
        activity.startActivity(intent);
    }

    private static void handle(List<MusicItem> musicItems, int position) {
        SongStore songStore = PlayerInstance.getSongStore();
        if (!songStore.songs().isEmpty()) {
            boolean b = checkCurrentSong(position, songStore, musicItems);
            if (b) {
                return;
            }
        }
        switchSongsAndPlay(musicItems, position);
    }

    private static boolean checkCurrentSong(int position, SongStore songStore, List<MusicItem> musicItems) {
        MusicItem nextSong = musicItems.get(position);
        MusicItem curSong = songStore.song(songStore.currentIndex());

        String curMid = curSong.getDescription().getSongMid();
        String nextMid = nextSong.getDescription().getSongMid();
        return curMid.equals(nextMid);
    }

    private static void switchSongsAndPlay(List<MusicItem> musicItems, int position) {
        MusicController musicController = PlayerInstance.getMusicController();
        musicController.replaceData(musicItems);
        musicController.switchSong(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.player_replace, PlayerFragment.newInstance()).commit();
        }
    }

}
