package com.yeguohao.music.api;

import com.yeguohao.music.javabean.TopSongList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Rank {

    @GET("https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg")
    Observable<com.yeguohao.music.javabean.Rank> getRank();

    @GET("https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg")
    Observable<TopSongList> topSongList(@Query("topid") String topId);

}
