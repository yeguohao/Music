package com.yeguohao.music.api;

import com.yeguohao.music.javabean.V8;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Singer {

    @GET("https://c.y.qq.com/v8/fcg-bin/v8.fcg?g_tk=1928093487&inCharset=utf-8&outCharset=utf-8&notice=0&format=jsonp&channel=singer&page=list&key=all_all_all&pagesize=100&pagenum=1&hostUin=0&needNewCode=0&platform=yqq&jsonpCallback=jp34")
    Observable<V8> v8();
}
