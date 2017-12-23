package com.yeguohao.music.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class RxDown {

    private static final String MUSIC_DOWN_CONFIG = "music_down_config";

    private static volatile RxDown rxDown;

    private OkHttpClient client;

    private ExecutorService executorService;

    private long block;
    private String url;
    private Request request;

    private SharedPreferences preferences;

    public void attach(Application application) {
        preferences = application.getSharedPreferences(MUSIC_DOWN_CONFIG, Context.MODE_PRIVATE);
    }

    public static RxDown getRxDown() {
        if (rxDown == null) {
            synchronized (RxDown.class) {
                if (rxDown == null) {
                    rxDown = new RxDown();
                }
            }
        }
        return rxDown;
    }

    private RxDown() {
        client = new OkHttpClient.Builder()
                .build();
        ThreadFactory threadFactory = new ThreadFactory() {
            int id;

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, id + "");
                id++;
                return thread;
            }
        };
        executorService = Executors.newCachedThreadPool(threadFactory);
    }


    public static void url(String url) {
        RxDown rxDown = getRxDown();
        rxDown.url = url;
        rxDown.request = new Request.Builder()
                .url(url)
                .build();
        rxDown.run();
    }

    private void run() {
        executorService.execute(this::fetch);
    }

    private void fetch() {
        long fileSize = fileSize(url);
        if (fileSize == 0) return;

        block = fileSize % 3 == 0 ? fileSize / 3 : fileSize / 3 + 1;
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
    }

    private long adjustStart(String fileName, long start) {
        File file = new File(fileName);
        if (file.exists()) {
            return file.length();
        }
        return start;
    }

    private Runnable task = () -> {
        int id = Integer.parseInt(Thread.currentThread().getName()) - 1;
        long start = id * block;
        long end = (id + 1) * block - 1;

        String fileName = "filePart" + id + "@range=" + start + "-" + end;
        long adjustStart = adjustStart(fileName, start);
        System.out.println("start: " + start + "-" + "adjustStart: " + adjustStart + "-" + "end: " + end);
        if (adjustStart > end) {
            mergeFilePart(start, fileName);
            return;
        }

        Request r = request.newBuilder()
                .header("range", "bytes=" + adjustStart + "-" + end)
                .build();
        try {
            Response response = client.newCall(r).execute();
            filePartWrite(response.body().bytes(), adjustStart, start, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private void filePartWrite(byte[] result, long adjustStart, long start, String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(adjustStart);
            accessFile.write(result);
            accessFile.close();
            mergeFilePart(start, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeFilePart(long start, byte[] result) {
        File file = new File("app.apk");
        try {
            file.createNewFile();
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(start);
            accessFile.write(result);
            accessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeFilePart(long start, String fileName) {
        File file = new File(fileName);
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(fileName, "r");
            byte[] result = new byte[(int) file.length()];
            accessFile.read(result);
            mergeFilePart(start, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long fileSize(String url) {
        Request request = new Request.Builder().url(url).head().build();
        Call call = client.newCall(request);
        Response response;
        try {
            response = call.execute();
            String length = response.header("Content-Length", "0");
            assert length != null;
            return Long.parseLong(length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        RxDown.url("http://127.0.0.1:8080/appv1.1.0.apk");
        System.out.println((System.currentTimeMillis() - startTime));
    }
}
