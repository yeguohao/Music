package com.yeguohao.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MediaPlayerService extends Service {

    private Uri uri;

    @Override
    public void onCreate() {
        super.onCreate();
        uri = Uri.fromFile(null);
        MediaPlayer.create(this, uri);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
