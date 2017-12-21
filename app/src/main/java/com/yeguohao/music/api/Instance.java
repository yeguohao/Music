package com.yeguohao.music.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yeguohao.music.api.Interceptor.FilterJsonPInterceptor;
import com.yeguohao.music.api.Interceptor.ParamsInterceptor;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instance {

    private volatile OkHttpClient client;

    private OkHttpClient getClient() {
        if (client == null) {
            synchronized (Instance.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .addInterceptor(new ParamsInterceptor())
                            .addInterceptor(new FilterJsonPInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
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
