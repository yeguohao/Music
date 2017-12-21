package com.yeguohao.music.api;

import com.yeguohao.music.javabean.V8;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Singer {

    @GET("https://c.y.qq.com/v8/fcg-bin/v8.fcg")
    Observable<V8> v8();
}
