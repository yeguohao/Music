package com.yeguohao.music;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.yeguohao.music.common.player.MediaPlayerUtil;
import com.yeguohao.music.common.RxDown;

public class MusicApplication extends Application {

    private static final String TAG = "MusicApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        RxDown.getRxDown().attach(this);
        MediaPlayerUtil.getPlayerUtil().setApplication(this);
    }

}
