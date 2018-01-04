package com.yeguohao.music.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.yeguohao.music.components.player.Song;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private MusicDown musicDown;
    private MediaPlayer player = new MediaPlayer();
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private SharedPreferences preferences;

    private List<Song> songs = new ArrayList<>();
    private Song currentSong;
    private int mode = SEQUENCE;

    private int state = NOT_LOADED;
    private int curInx;
    private int curTime;

    private List<OnStateListener> stateListeners = new ArrayList<>();
    private List<MediaPlayer.OnCompletionListener> completionListeners = new ArrayList<>();

    private Recently recently = new Recently();
    private CountDownTimer countDownTimer = new CountDownTimer(1000 * 60 * 60 * 24 * 7, 100) {
        @Override
        public void onTick(long millisUntilFinished) {
            tick();
        }

        @Override
        public void onFinish() {

        }
    };

    private void tick() {
        int currentPosition = player.getCurrentPosition();
        int duration = player.getDuration();
        curTime = currentPosition;

        int percent = (int) ((double) currentPosition / (double) duration * 100);
        for (OnStateListener stateListener : stateListeners) {
            stateListener.onPlayProgress(percent, currentPosition);
        }
    }

    private String absolutePath;

    private MediaPlayerUtil() {
    }

    public void storeState() {
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> set = new LinkedHashSet<>();
        for (Song song : songs) {
            song.put(editor);
            set.add(song.getSongMid());
        }
        editor.putInt("mode", mode)
                .putInt("last_time", player.getCurrentPosition())
                .putInt("last_index", curInx)
                .putStringSet("songs", set)
                .apply();
    }

    public void restoreState() {
        mode = preferences.getInt("mode", SEQUENCE);
        Set<String> set = preferences.getStringSet("songs", null);
        if (set != null) {
            for (String mid : set) {
                Song song = new Song();
                song.setSongMid(mid);
                song.get(preferences);
                songs.add(song);
            }
        }
        curInx = preferences.getInt("last_index", 0);
        if (songs.isEmpty()) return;
        currentSong = songs.get(curInx);
        loadInitial(currentSong.getSongMid(), preferences.getInt("last_time", 0));
    }

    public static MediaPlayerUtil getPlayerUtil() {
        return playerUtil;
    }

    public void setCompletionListener(MediaPlayer.OnCompletionListener listener) {
        completionListeners.add(listener);
    }

    public void setApplication(Application application) {
        this.application = application;
        preferences = application.getSharedPreferences("state", Context.MODE_PRIVATE);
        musicDown = new MusicDown(application);
        player.setOnCompletionListener(mp -> mainThreadHandler.post(() -> {
            for (MediaPlayer.OnCompletionListener listener : completionListeners) {
                listener.onCompletion(mp);
            }
        }));
        restoreState();
    }

    private void loadInitial(String songMid, int curTime) {
        if (TextUtils.isEmpty(songMid)) return;
        this.curTime = curTime;
        FileDescriptor fd = getFdByPath(absolutePath + "/" + songMid);
        if (fd == null) return;
        try {
            player.setDataSource(fd);
            player.prepare();
            player.seekTo(curTime);
            state = LOADED;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileDescriptor getFdByPath(String path) {
        FileDescriptor fd = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            fd = fis.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fd;
    }

    @Override
    public void downDone(String path) {
        if (!path.contains(currentSong.getSongMid())) return;
        FileDescriptor finalFd = getFdByPath(path);
        if (finalFd == null) {
            for (OnStateListener stateListener : stateListeners) {
                stateListener.onPlayFailed();
            }
            return;
        }

        mainThreadHandler.post(() -> {
            try {
                player.reset();
                player.setDataSource(finalFd);
                player.prepare();
                state = LOADED;
                currentSong.setDuration(player.getDuration());
                for (OnStateListener stateListener : stateListeners) {
                    stateListener.onLoaded();
                }
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
        tick();
        countDownTimer.start();
    }

    public void pause() {
        if (player.isPlaying()) {
            player.pause();
            countDownTimer.cancel();
        }
    }

    public Song nextSong() {
        pause();
        state = NOT_LOADED;
        int inx = getIndex(NEXT);
        load(inx);
        return songs.get(inx);
    }

    public Song prevSong() {
        pause();
        state = NOT_LOADED;
        int inx = getIndex(PREV);
        load(inx);
        return songs.get(inx);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    public void load(int index) {
        if (index < 0 || index >= songs.size() || (index == curInx && state == LOADED)) {
            return;
        }
        pause();
        curInx = index;
        state = LOADING;
        currentSong = songs.get(index);
        absolutePath = application.getFilesDir().getAbsolutePath();
        String url = String.format(MUSIC_DOWN_BASE_URL, currentSong.getSongMid());
        String songMid = currentSong.getSongMid();
        new Thread() {
            @Override
            public void run() {
                musicDown.down(application, url, absolutePath, songMid, MediaPlayerUtil.this);
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

    public int getCurTime() {
        Log.e(TAG, "getCurrentPosition: " + player.getCurrentPosition());
        return curTime;
    }

    public int getDuration() {
        return currentSong.getDuration();
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
        return currentSong.isFavorite();
    }

    public void setFavorite(boolean favorite) {
        currentSong.setFavorite(favorite);
    }

    public int getState() {
        return state;
    }

    public void setStateListener(OnStateListener stateListener) {
        stateListeners.add(stateListener);
    }

    public interface OnStateListener {

        void onLoaded();

        void onPlayProgress(int percent, int currentPosition);

        void onPlayFailed();
    }
}
