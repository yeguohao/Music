package com.yeguohao.music.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Player {

    @GET("http://ustbhuangyi.com/music/api/lyric?g_tk=1928093487&inCharset=utf-8&outCharset=utf-8&notice=0&format=json&platform=yqq&hostUin=0&needNewCode=0&categoryId=10000000&pcachetime=1513668010530")
    Observable<Object> getLyric(@Query("songmid") String songmid);

}
