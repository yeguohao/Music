package com.yeguohao.music.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Player {

    @GET("http://ustbhuangyi.com/music/api/lyric")
    Observable<Object> getLyric(@Query("songmid") String songmid);

}
