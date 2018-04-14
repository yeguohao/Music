package com.yeguohao.music.common.player.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongStore {

    private List<MusicItem> songs;
    private List<MusicItem> recentlySongs;
    private List<MusicItem> flavourSongs;
    private int currentIndex = -1;

    public SongStore() {
        songs = new ArrayList<>();
        recentlySongs = new LinkedList<>();
        flavourSongs = new LinkedList<>();
    }

    public int currentIndex() {
        return currentIndex;
    }

    public MusicItem song(int index) {
        return songs.get(index);
    }

    public List<MusicItem> songs() {
        return songs;
    }

    public void removeSong(MusicItem item) {
        songs.remove(item);
    }

    void add(MusicItem item) {
        if (item != null && !songs.contains(item)) {
            songs.add(item);
        }
    }

    void replaceData(List<MusicItem> data) {
        songs.clear();
        songs.addAll(data);
    }

    void currentIndex(int index) {
        currentIndex = index;
    }

    void addRecentlySong(MusicItem item) {
        int i = recentlySongs.indexOf(item);
        if (i != -1) {
            recentlySongs.remove(i);
        }
        recentlySongs.add(item);
    }

    public List<MusicItem> recentlySongs() {
        return recentlySongs;
    }

    void addFlavourSong(MusicItem item) {
        int i = flavourSongs.indexOf(item);
        if (i != -1) {
            return;
        }
        flavourSongs.add(item);
    }

    void removeFlavourSong(MusicItem item) {
        flavourSongs.remove(item);
    }

    public List<MusicItem> flavourSongs() {
        return flavourSongs;
    }

}
