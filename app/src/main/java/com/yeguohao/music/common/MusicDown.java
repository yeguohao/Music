package com.yeguohao.music.common;

import android.content.Context;

import com.bumptech.glide.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yeguohao.music.common.DownUtil.getAppVersion;
import static com.yeguohao.music.common.DownUtil.getDiskCacheDir;
import static com.yeguohao.music.common.DownUtil.hashKeyForDisk;

public class MusicDown {

    public static final int MAX_SIZE = 100 * 1024 * 1024;

    private OkHttpClient client = new OkHttpClient.Builder()
            .build();

    private Context context;
    private MusicDownListener listener;

    private DiskLruCache lruCache;

    public MusicDown(Context context, MusicDownListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void down(String url, String fileName) {
        try {
            String key = hashKeyForDisk(url + fileName);
            lruCache = DiskLruCache.open(getDiskCacheDir(context, fileName), getAppVersion(context), 1, MAX_SIZE);
            DiskLruCache.Value value = lruCache.get(key);
            if (value == null) {
                fetchDataFromNetwork(url, key);
            } else {
                fetchDataFromCache(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchDataFromCache(DiskLruCache.Value key) {
        listener.downDone(key.getFile(0));
    }

    private void fetchDataFromNetwork(String url, String fileName) {
        File file = new File(fileName, "rw");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            BufferedInputStream bis = new BufferedInputStream(response.body().byteStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

            byte[] temp = new byte[1024];
            while (bis.read(temp) != -1) {
                bos.write(temp);
            }
            bos.flush();
            bos.close();
            bis.close();
            DiskLruCache.Editor editor = lruCache.edit(fileName);
            editor.set(0, fileName);
            editor.commit();

            listener.downDone(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface MusicDownListener {
        void downDone(File file);
    }
}
