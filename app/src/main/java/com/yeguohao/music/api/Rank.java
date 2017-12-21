package com.yeguohao.music.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Rank {

    @GET("https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg")
    Observable<com.yeguohao.music.javabean.Rank> getRank();

}
