package com.yeguohao.music.common.player;

import com.yeguohao.music.common.player.impl.PlayerImpl;
import com.yeguohao.music.common.player.impl.SongStore;
import com.yeguohao.music.common.player.interfaces.MusicController;

public class PlayerInstance {

    static MusicController musicController;
    static SongStore songStore;
    static PlayerImpl.PositionUpdater updater;

    public static MusicController getMusicController() {
        return musicController;
    }

    public static SongStore getSongStore() {
        return songStore;
    }

    public static PlayerImpl.PositionUpdater getUpdater() {
        return updater;
    }

}
