package com.yeguohao.music.common.player.interfaces;

import com.yeguohao.music.common.player.impl.MusicItem;

import java.util.List;

public interface MusicController {
    void next();
    void prev();
    void play();
    void pause();
    void favorite();
    void favorite(MusicItem item);
    void seek(long position);
    void switchMode();
    int getMode();
    void switchSong(int index);
    void add(MusicItem mediaItem);
    void replaceData(List<MusicItem> data);
}
