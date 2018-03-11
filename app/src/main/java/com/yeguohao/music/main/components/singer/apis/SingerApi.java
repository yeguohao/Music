package com.yeguohao.music.main.components.singer.apis;

import com.yeguohao.music.javabean.SingerSongList;
import com.yeguohao.music.javabean.V8;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SingerApi {

    @GET("https://c.y.qq.com/v8/fcg-bin/v8.fcg")
    Observable<V8> v8();

    @GET("https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg")
    Observable<SingerSongList> songList(@Query("singermid") String singerMid);
}
