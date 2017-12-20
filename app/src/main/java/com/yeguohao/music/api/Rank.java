package com.yeguohao.music.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Rank {

    @GET("https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg?g_tk=1928093487&inCharset=utf-8&outCharset=utf-8&notice=0&format=jsonp&uin=0&needNewCode=1&platform=h5&jsonpCallback=jp34")
    Observable<com.yeguohao.music.javabean.Rank> getRank();

}
