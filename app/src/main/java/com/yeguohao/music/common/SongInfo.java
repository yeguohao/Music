package com.yeguohao.music.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import static com.yeguohao.music.components.player.PlayerConstance.ALBUM_MID;
import static com.yeguohao.music.components.player.PlayerConstance.SINGER;
import static com.yeguohao.music.components.player.PlayerConstance.SONG;
import static com.yeguohao.music.components.player.PlayerConstance.SONG_MID;
import static com.yeguohao.music.components.player.PlayerConstance.STORE_FILE_NAME;

public class SongInfo {

    private static final SongInfo INFO = new SongInfo();

    public static SongInfo newInstance() {
        return INFO;
    }

    private String songName;
    private String singerName;
    private String songMid;
    private String albumMid;
    private boolean isPause;

    private List<PauseChanged> pauseListener = new ArrayList<>();

    public static void restoreInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STORE_FILE_NAME,Context.MODE_PRIVATE);

        SongInfo info = newInstance();
        info.setSongName(preferences.getString(SONG, ""));
        info.setSingerName(preferences.getString(SINGER, ""));
        info.setSongMid(preferences.getString(SONG_MID, ""));
        info.setAlbumMid(preferences.getString(ALBUM_MID, ""));
        info.setPause(true);
    }

    public static void storeInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STORE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        SongInfo info = newInstance();
        editor.putString(SONG, info.songName)
                .putString(SONG_MID, info.songMid)
                .putString(ALBUM_MID, info.albumMid)
                .putString(SINGER, info.singerName)
                .apply();

        info.dumpListener();
    }

    private void dumpListener() {
        pauseListener.clear();
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongMid() {
        return songMid;
    }

    public void setSongMid(String songMid) {
        this.songMid = songMid;
    }

    public String getAlbumMid() {
        return albumMid;
    }

    public void setAlbumMid(String albumMid) {
        this.albumMid = albumMid;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
        for (PauseChanged listener : pauseListener) {
            listener.onPauseChange(pause);
        }
    }

    public void setPauseListener(PauseChanged listener) {
        pauseListener.add(listener);
    }

    public interface PauseChanged {
        void onPauseChange(boolean isPause);
    }
}
