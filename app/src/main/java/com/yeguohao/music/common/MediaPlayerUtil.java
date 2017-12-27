package com.yeguohao.music.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.yeguohao.music.components.player.Song;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.yeguohao.music.components.player.PlayerConstance.MUSIC_DOWN_BASE_URL;
import static com.yeguohao.music.components.player.PlayerConstance.RANDOM;
import static com.yeguohao.music.components.player.PlayerConstance.SEQUENCE;

public class MediaPlayerUtil implements MusicDown.MusicDownListener {

    private static final int NEXT = 0;
    private static final int PREV = 1;
    private static final int NOT_LOADED = 2;
    public static final int LOADING = 3;
    public static final int LOADED = 4;
    public static final int LOAD_FAILED = 5;

    private static final String TAG = "MediaPlayerUtil";
    private static MediaPlayerUtil playerUtil = new MediaPlayerUtil();

    private Application application;
    private MusicDown musicDown = new MusicDown();
    private MediaPlayer player = new MediaPlayer();
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private SharedPreferences preferences;

    private List<Song> songs = new ArrayList<>();
    private Song currentSong;
    private int mode = SEQUENCE;
    private boolean favorite;

    private int state = NOT_LOADED;
    private int curInx;

    private Recently recently = new Recently();

    private Thread progressThread = new Thread() {
        @Override
        public void run() {
            while (player.isPlaying()) {
                int curPos = player.getCurrentPosition();
                int duration = player.getDuration();
                mainThreadHandler.post(() -> {
                    if (progressListener != null) {
                        progressListener.onProgress(curPos, duration);
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
                player.reset();
                player.setDataSource(finalFd);
                player.prepare();
                state = LOADED;
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void onFailed(int code) {
        if (state == LOADING) {
            state = LOAD_FAILED;
        }
    }

    private int getIndex(int key) {
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
        return inx;
    }

    public void start() {
        player.start();
        progressThread.start();
    }

    public void pause() {
        if (player.isPlaying()) {
            player.pause();
        }
    }

    public Song next() {
        state = NOT_LOADED;
        int inx = getIndex(NEXT);
        load(inx);
        return songs.get(inx);
    }

    public Song prev() {
        state = NOT_LOADED;
        int inx = getIndex(PREV);
        load(inx);
        return songs.get(inx);
    }

    public void toggleMode(int mode) {
        this.mode = mode;
    }

    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    public void load(int index) {
        if (index < 0 || index >= songs.size() || (index == curInx && state == LOADED)) {
            return;
        }
        state = LOADING;
        currentSong = songs.get(index);
        String dir = application.getFilesDir().getAbsolutePath();
        String url = String.format(MUSIC_DOWN_BASE_URL, currentSong.getSongMid());
        String fileName = currentSong.getSongMid();
        new Thread() {
            @Override
            public void run() {
                musicDown.down(application, url, dir, fileName, MediaPlayerUtil.this);
            }
        }.start();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void addSongs(List<Song> songs) {
        this.songs.clear();
        this.songs.addAll(songs);
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public int getMode() {
        return mode;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public boolean isPlay() {
        return player.isPlaying();
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getState() {
        return state;
    }

    private OnPlayProgressListener progressListener;

    public void setProgressListener(OnPlayProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public interface OnPlayProgressListener {
        void onProgress(int curPos, int duration);
    }

    public interface OnErrorListener {

    }
}
