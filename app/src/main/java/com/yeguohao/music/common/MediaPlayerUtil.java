package com.yeguohao.music.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.yeguohao.music.components.player.PlayerConstance.MUSIC_DOWN_BASE_URL;
import static com.yeguohao.music.components.player.PlayerConstance.RANDOM;
import static com.yeguohao.music.components.player.PlayerConstance.SEQUENCE;

public class MediaPlayerUtil implements MusicDown.MusicDownListener {

    public static final int START = 1 << 1;
    public static final int PAUSE = 1 << 2;
    public static final int NEXT = 1 << 3;
    public static final int PREV = 1 << 4;
    public static final int FAVORITE = 1 << 5;
    public static final int MODE = 1 << 6;
    public static final int PROGRESS = 1 << 7;
    public static final int MATH_THREAD = 1 << 8;
    public static final int AT_ONCE = 1 << 9;

    private static final String TAG = "MediaPlayerUtil";
    private static MediaPlayerUtil playerUtil = new MediaPlayerUtil();

    private Application application;
    private MusicDown musicDown = new MusicDown();
    private MediaPlayer player = new MediaPlayer();
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private SharedPreferences preferences;

    public static class Song {
        private String songName;
        private String singerName;
        private String songMid;
        private String albumMid;

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
    }

    private List<Song> songs = new ArrayList<>();
    private Song currentSong;
    private int mode = SEQUENCE;
    private boolean favorite;

    private boolean first = true;

    private Recently recently = new Recently();

    private List<MediaPlayerListener> startListeners = new ArrayList<>();
    private List<MediaPlayerListener> pauseListeners = new ArrayList<>();
    private List<MediaPlayerListener> songChangedListeners = new ArrayList<>();
    private List<MediaPlayerListener> favoriteListeners = new ArrayList<>();
    private List<MediaPlayerListener> modeChangedListeners = new ArrayList<>();
    private List<MediaPlayerListener> progressChangedListeners = new ArrayList<>();

    private Thread progressThread = new Thread() {
        @Override
        public void run() {
            while (player.isPlaying()) {
                int curPos = player.getCurrentPosition();
                mainThreadHandler.post(() -> {
                    for (MediaPlayerListener cb : progressChangedListeners) {
                        cb.progress(curPos);
                    }
                });
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private MediaPlayerUtil() {
    }

    public static MediaPlayerUtil getPlayerUtil() {
        return playerUtil;
    }

    public void on(int key, MediaPlayerListener cb) {
        if (cb == null) return;
        if ((key & START) != 0) {
            startListeners.add(cb);
        }
        if ((key & PAUSE) != 0) {
            pauseListeners.add(cb);
        }
        if ((key & NEXT) != 0) {
            songChangedListeners.add(cb);
        }
        if ((key & PREV) != 0) {
            songChangedListeners.add(cb);
        }
        if ((key & FAVORITE) != 0) {
            favoriteListeners.add(cb);
        }
        if ((key & PROGRESS) != 0) {
            progressChangedListeners.add(cb);
        }
        if ((key & MODE) != 0) {
            modeChangedListeners.add(cb);
        }

        if ((key & AT_ONCE) != 0) {
            if (currentSong != null) cb.songChanged(currentSong);
            cb.modeChanged(mode);
            cb.favorite(favorite);
            cb.pause();
            cb.progress(0);
        }
    }

    public void sent(int key, Object obj) {
        if ((key & START) != 0) {
            if (first) {
                loadSong();
                first = false;
            } else {
                start();
            }
        } else if ((key & PAUSE) != 0) {
            pause();
        } else if ((key & NEXT) != 0) {
            changeCurrentSong(NEXT);
        } else if ((key & PREV) != 0) {
            changeCurrentSong(PREV);
        } else if ((key & FAVORITE) != 0) {
            changeFavorite();
        } else if ((key & MODE) != 0) {
            if (obj == null || !(obj instanceof Integer)) {
                throw new IllegalArgumentException("mode should not is null!");
            }
            mode = (int) obj;
            for (MediaPlayerListener cb : modeChangedListeners) {
                cb.modeChanged(mode);
            }
        } else if ((key & PROGRESS) != 0) {
            if (obj == null || !(obj instanceof Integer)) {
                throw new IllegalArgumentException("progress should not is null!");
            }
            player.seekTo((Integer) obj);
        }
    }

    public void sent(int key) {
        sent(key, null);
    }

    private void changeFavorite() {
        favorite = !favorite;
        preferences.edit().putBoolean(currentSong.songMid, favorite).apply();
        for (MediaPlayerListener cb : favoriteListeners) {
            cb.favorite(favorite);
        }
    }

    private void changeCurrentSong(int key) {
        int curInx = songs.indexOf(currentSong);
        int inx = curInx;
        int size = songs.size();
        if (mode == SEQUENCE) {
            if (key == NEXT) {
                inx = curInx == size - 1 ? 0 : curInx + 1;
            } else {
                inx = curInx == 0 ? size - 1 : curInx - 1;
            }
        } else if (mode == RANDOM) {
            inx = recently.random(size);
        }
        player.stop();
        currentSong = songs.get(inx);
        loadSong();

        for (MediaPlayerListener cb : songChangedListeners) {
            cb.songChanged(currentSong);
        }
    }

    public void setApplication(Application application) {
        this.application = application;
        preferences = application.getSharedPreferences("aabbcc", Context.MODE_PRIVATE);
    }

    @Override
    public void downDone(String path) {
        FileDescriptor fd = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            fd = fis.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDescriptor finalFd = fd;

        mainThreadHandler.post(() -> {
            try {
                player.setDataSource(finalFd);
                player.prepare();
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void setSongs(List<Song> songs, int index) {
        if (currentSong != null && songs.get(index).getSongMid().equals(currentSong.songMid)) {
            return;
        }
        this.songs.clear();
        this.songs.addAll(songs);
        currentSong = this.songs.get(index);
        loadSong();
    }

    private void loadSong() {
        String dir = application.getFilesDir().getAbsolutePath();
        String url = String.format(MUSIC_DOWN_BASE_URL, currentSong.songMid);
        String fileName = currentSong.songMid;
        new Thread() {
            @Override
            public void run() {
                musicDown.down(application, url, dir, fileName, MediaPlayerUtil.this);
            }
        }.start();
    }

    private void start() {
        player.start();
        progressThread.start();
        for (MediaPlayerListener cb : startListeners) {
            cb.start();
        }
    }

    private void pause() {
        if (player.isPlaying()) {
            player.pause();
            for (MediaPlayerListener cb : pauseListeners) {
                cb.pause();
            }
        }
    }
}
