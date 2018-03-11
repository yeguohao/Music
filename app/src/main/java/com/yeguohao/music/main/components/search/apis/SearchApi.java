package com.yeguohao.music.main.components.search.apis;

import com.yeguohao.music.javabean.HotKey;
import com.yeguohao.music.javabean.SearchInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {

    @GET("https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg")
    Observable<HotKey> getHotKey();

    @GET("https://c.y.qq.com/soso/fcgi-bin/search_for_qq_cp?p=1&perpage=20&n=20")
    Observable<SearchInfo> search(@Query("w") String encodeKey);
}
