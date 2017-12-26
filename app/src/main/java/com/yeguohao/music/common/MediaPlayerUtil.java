package com.yeguohao.music.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

public class MediaPlayerUtil {

    private static MediaPlayerUtil util = new MediaPlayerUtil();

    private static MediaPlayer player;
    public static MediaPlayerUtil getUtil() {
        return util;
    }

    public void init(Context context, String file) {
        player = MediaPlayer.create(context, Uri.fromFile(new File(file)));
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }
}
