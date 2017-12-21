package com.yeguohao.music.api;

import com.yeguohao.music.javabean.CdInfo;
import com.yeguohao.music.javabean.DiscList;
import com.yeguohao.music.javabean.RecommendBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Recommend {

    @GET("https://c.y.qq.com/musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg")
    Observable<RecommendBean> getRecommend();

    @GET("http://ustbhuangyi.com/music/api/getDiscList")
    Observable<DiscList> getDiscList();

    @GET("http://ustbhuangyi.com/music/api/getCdInfo")
    Observable<CdInfo> getCdInfo(@Query("disstid") String disstid);
}
