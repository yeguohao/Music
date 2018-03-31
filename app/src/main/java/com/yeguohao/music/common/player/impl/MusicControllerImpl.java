package com.yeguohao.music.common.player.impl;

import com.yeguohao.music.common.player.interfaces.MusicController;
import com.yeguohao.music.common.player.interfaces.Player;

import java.util.List;

import static com.yeguohao.music.player.PlayerConstance.RANDOM;
import static com.yeguohao.music.player.PlayerConstance.SEQUENCE;

public class MusicControllerImpl implements MusicController {

    private static final int NEXT = 0;
    private static final int PREV = 1;

    private Player player;
    private SongStore songStore;
    private RecentlyPlaySongs recentlyPlaySongs;

    private int mode = SEQUENCE;

    public MusicControllerImpl(Player player, SongStore songStore) {
        this.player = player;
        this.songStore = songStore;
        this.recentlyPlaySongs = new RecentlyPlaySongs();
    }

    private int getIndexByModeAndOperation(int key) {
        int currentIndex = songStore.currentIndex();
        List<MusicItem> songs = songStore.songs();
        int index = currentIndex;
        int size = songs.size();

        if (mode == SEQUENCE) {
            if (key == NEXT) {
                index = currentIndex == size - 1 ? 0 : currentIndex + 1;
            } else {
                index = currentIndex == 0 ? size - 1 : currentIndex - 1;
            }
        } else if (mode == RANDOM) {
            index = recentlyPlaySongs.random(size);
        }
        return index;
    }

    @Override
    public void next() {
        int next = getIndexByModeAndOperation(NEXT);
        songStore.currentIndex(next);
        player.play();
    }

    @Override
    public void prev() {
        int prev = getIndexByModeAndOperation(PREV);
        songStore.currentIndex(prev);
        player.play();
    }

    @Override
    public void play() {
        player.play();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void favorite() {
        MusicItem item = songStore.song(songStore.currentIndex());
        favorite(item);
    }

    @Override
    public void favorite(MusicItem item) {
        item.setFavorite(!item.isFavorite());
        if (item.isFavorite()) {
            songStore.addFlavourSong(item);
        } else {
            songStore.removeFlavourSong(item);
        }
    }

    @Override
    public void seek(long position) {
        player.seek(position);
    }

    @Override
    public void switchMode() {
        mode++;
        if (mode > RANDOM) {
            mode = SEQUENCE;
        }
    }

    @Override
    public void switchSong(int index) {
        songStore.currentIndex(index);
        player.play();
    }

    @Override
    public void add(MusicItem mediaItem) {
        songStore.add(mediaItem);
    }

    @Override
    public void replaceData(List<MusicItem> data) {
        songStore.replaceData(data);
    }

    @Override
    public int getMode() {
        return mode;
    }

}
