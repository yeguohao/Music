package com.yeguohao.music.common;


public interface MediaPlayerListener {

    default void start() {

    }

    default void pause() {

    }

    default void next() {

    }

    default void prev() {

    }

    default void songChanged(MediaPlayerUtil.Song song) {

    }

    default void modeChanged(int mode) {

    }

    default void favorite(boolean isFavorite) {

    }

    default void progress(int progress) {

    }
}
