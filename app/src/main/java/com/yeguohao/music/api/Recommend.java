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

    @GET("http://ustbhuangyi.com/music/api/getDiscList?g_tk=1928093487&inCharset=utf-8&outCharset=utf-8&notice=0&format=json&platform=yqq&hostUin=0&sin=0&ein=29&sortId=5&needNewCode=0&categoryId=10000000&rnd=0.9889400562688042")
    Observable<DiscList> getDiscList();

    @GET("http://ustbhuangyi.com/music/api/getCdInfo?g_tk=1928093487&inCharset=utf-8&outCharset=utf-8&notice=0&format=jsonp&type=1&json=1&utf8=1&onlysong=0&platform=yqq&hostUin=0&needNewCode=0")
    Observable<CdInfo> getCdInfo(@Query("disstid") String disstid);
}
