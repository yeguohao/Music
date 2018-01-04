package com.yeguohao.music.common;

public class ProgressPercent {

    public int percent(long cur, long total) {
        double percent = (double) cur / (double) total * 100;
        return (int) percent;
    }

}
