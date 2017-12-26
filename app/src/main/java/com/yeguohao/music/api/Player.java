package com.yeguohao.music.api;

import com.yeguohao.music.javabean.Lyric;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Player {

    @GET("http://ustbhuangyi.com/music/api/lyric")
    Observable<Lyric> getLyric(@Query("songmid") String songMid);

}
