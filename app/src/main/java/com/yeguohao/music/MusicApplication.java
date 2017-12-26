package com.yeguohao.music;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.yeguohao.music.common.MediaPlayerUtil;
import com.yeguohao.music.common.RxDown;
import com.yeguohao.music.common.SongInfo;

public class MusicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        SongInfo.restoreInfo(this);
        RxDown.getRxDown().attach(this);
        MediaPlayerUtil.getPlayerUtil().setApplication(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SongInfo.storeInfo(this);
    }
}
