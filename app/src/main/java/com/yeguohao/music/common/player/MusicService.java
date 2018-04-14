package com.yeguohao.music.common.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yeguohao.music.common.player.impl.MusicControllerImpl;
import com.yeguohao.music.common.player.impl.PlayerImpl;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;


public class MusicService extends Service {

    @Override
    public void onCreate() {
        SongStore songStore = new SongStore();
        PlayerImpl player = new PlayerImpl(songStore, this);
        MusicController musicController = new MusicControllerImpl(player, songStore);
        PlayerInstance.musicController = musicController;
        PlayerInstance.songStore = songStore;
        PlayerInstance.updater = player.getUpdater();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
