package com.yeguohao.music.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yeguohao.music.api.Interceptor.FilterJsonPInterceptor;
import com.yeguohao.music.api.Interceptor.ParamsInterceptor;
import com.yeguohao.music.api.Interceptor.UserAgentInterceptor;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private RetrofitInstance() {}

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new ParamsInterceptor())
            .addInterceptor(new FilterJsonPInterceptor())
            .addInterceptor(new UserAgentInterceptor())
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://c.y.qq.com")
            .build();

    public static Retrofit Retrofit() {
        return retrofit;
    }

}
