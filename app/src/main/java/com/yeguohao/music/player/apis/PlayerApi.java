package com.yeguohao.music.player.apis;

import com.yeguohao.music.javabean.Lyric;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlayerApi {

    @GET("http://ustbhuangyi.com/music/api/lyric")
    Observable<Lyric> getLyric(@Query("songmid") String songMid);

}
