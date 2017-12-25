package com.yeguohao.music;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.yeguohao.music.common.RxDown;
import com.yeguohao.music.common.SongInfo;

public class MusicApplication extends Application {

    private static final String TAG = "MusicApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        SongInfo.restoreInfo(this);
        RxDown.getRxDown().attach(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG, "onTerminate: " );
        SongInfo.storeInfo(this);
    }
}
