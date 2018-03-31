package com.yeguohao.music;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MusicApplication extends Application {

    private static final String TAG = "MusicApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
