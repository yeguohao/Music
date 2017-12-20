package com.yeguohao.music.api;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instance {

    private static final String TAG = "Instance";
    private volatile OkHttpClient client;

    private OkHttpClient getClient() {
        if (client == null) {
            synchronized (Instance.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
//                            .addInterceptor(new Inter())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    Response response = chain.proceed(request);
                                    ResponseBody body = response.body();
                                    String result = body.string();

                                    String url = request.url().toString();
                                    if (url.contains("https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg") ||
                                            url.contains("https://c.y.qq.com/v8/fcg-bin/v8.fcg")) {
                                        result = result.replace("jp34(", "").replace(")", "");

                                        ResponseBody realBody = ResponseBody.create(body.contentType(), result);
                                        return response.newBuilder().body(realBody).build();
                                    } else if (request.url().toString().contains("http://ustbhuangyi.com/music/api/getCdInfo")) {

                                        Log.e(TAG, "url: " + request.url().toString());
                                        Log.e(TAG, "result: " + result);

                                        ResponseBody realBody = ResponseBody.create(body.contentType(), result);
                                        return response.newBuilder().body(realBody).build();
                                    }

                                    return chain.proceed(request);
                                }
                            })
                            .build();
                }
            }
        }
        return client;
    }

    private volatile Retrofit retrofit;

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Instance.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .client(getClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory
                                    .createWithScheduler(Schedulers.io()))
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://c.y.qq.com")
                            .build();
                }
            }
        }
        return retrofit;
    }

    private volatile Recommend recommend;

    public Recommend Recommend() {
        if (recommend == null) {
            synchronized (Instance.class) {
                if (recommend == null) {
                    recommend = getRetrofit().create(Recommend.class);
                }
            }
        }
        return recommend;
    }

    private volatile Player player;

    public Player Player() {
        if (player == null) {
            synchronized (Instance.class) {
                if (player == null) {
                    player = getRetrofit().create(Player.class);
                }
            }
        }
        return player;
    }

    private volatile Rank rank;

    public Rank Rank() {
        if (rank == null) {
            synchronized (Instance.class) {
                if (rank == null) {
                    rank = getRetrofit().create(Rank.class);
                }
            }
        }
        return rank;
    }

    private volatile Singer singer;

    public Singer Singer() {
        if (singer == null) {
            synchronized (Instance.class) {
                if (singer == null) {
                    singer = getRetrofit().create(Singer.class);
                }
            }
        }
        return singer;
    }
}
