package com.yeguohao.music.common;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yeguohao.music.common.DownUtil.getAppVersion;
import static com.yeguohao.music.common.DownUtil.getDiskCacheDir;
import static com.yeguohao.music.common.DownUtil.hashKeyForDisk;

public class MusicDown {

    private static final String TAG = "MusicDown";
    public static final int MAX_SIZE = 100 * 1024 * 1024;

    private OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

    private DiskLruCache lruCache;

    public static final String CACHE_FILENAME = "music_cache";

    public MusicDown(Context context) {
        try {
            lruCache = DiskLruCache.open(getDiskCacheDir(context, CACHE_FILENAME), getAppVersion(context), 1, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void down(Context context, String url, String fileDir, String songMid, MusicDownListener listener) {
        try {
            String key = hashKeyForDisk(url + songMid);
            DiskLruCache.Value value = lruCache.get(key);
            if (value == null) {
                fetchDataFromNetwork(url, key, fileDir + "/" + songMid, listener);
            } else {
                fetchDataFromCache(value, listener);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchDataFromCache(DiskLruCache.Value key, MusicDownListener listener) {
        if (listener != null) {
            File file = key.getFile(0);
            char[] chars = new char[56];
            try {
                FileReader fileReader = new FileReader(file);
                int n = fileReader.read(chars);
                Log.e(TAG, "fetchDataFromCache: " + n );
                Log.e(TAG, "fetchDataFromCache: " + new String(chars) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            listener.downDone(new String(chars));
        }
    }

    private void fetchDataFromNetwork(String url, String key, String fileName, MusicDownListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .header("Range", "bytes=0-")
                .build();
        Log.e(TAG, "fetchDataFromNetwork: " + url);
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            if (code == 200 || code == 206) {
                String type = response.body().contentType().subtype();
                fileName = fileName + "." + type;
                File file = new File(fileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "fetchDataFromNetwork: " + file.getAbsolutePath());
                byte[] result = response.body().bytes();
                Log.e(TAG, "result size: " + result.length);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(result);

                bos.flush();
                bos.close();
                DiskLruCache.Editor editor = lruCache.edit(key);
                editor.set(0, fileName);
                editor.commit();

                if (listener != null) {
                    listener.downDone(fileName );
                }
            } else {
                if (listener != null) {
                    listener.onFailed(code);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface MusicDownListener {
        void downDone(String path);

        void onFailed(int code);
    }
}
