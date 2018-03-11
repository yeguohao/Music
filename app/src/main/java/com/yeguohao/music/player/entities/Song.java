package com.yeguohao.music.player.entities;

import android.content.SharedPreferences;
import android.util.Log;

public class Song {

    private static final String TAG = "SongFragment";

    private String songName;
    private String singerName;
    private String songMid;
    private String albumMid;
    private boolean favorite;
    private int duration;

    public void setAlbumMid(String albumMid) {
        this.albumMid = albumMid;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public void setSongMid(String songMid) {
        this.songMid = songMid;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongName() {
        return songName;
    }

    public String getAlbumMid() {
        return albumMid;
    }

    public String getSingerName() {
        return singerName;
    }

    public String getSongMid() {
        return songMid;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void put(SharedPreferences.Editor editor) {
        String temp = songName + "," + singerName + "," + albumMid;
        editor.putString(songMid, temp);
        editor.putBoolean(songMid + "bool", favorite);
        editor.putInt(songMid + "int", duration);
    }

    public void get(SharedPreferences preferences) {
        String temp = preferences.getString(songMid, "");
        String[] strings = temp.split(",");
        Log.e(TAG, "get: " + temp );
        songName = strings[0];
        singerName = strings[1];
        albumMid = strings[2];
        favorite = preferences.getBoolean(songMid + "bool", false);
        duration = preferences.getInt(songMid + "int", 0);
    }
}