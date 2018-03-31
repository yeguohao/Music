package com.yeguohao.music.api.Interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamsInterceptor implements Interceptor {

    private static final String JSONP_CALLBACK = "jp34";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        HttpUrl.Builder builder = request.url().newBuilder();
        if (!url.contains("fcg_yqqhomepagerecommend.fcg")) commonParams(builder);

        if (url.contains("v8.fcg")) {
            v8Params(builder);
        } else if (url.contains("getCdInfo")) {
            getCdInfoParams(builder);
        } else if (url.contains("getDiscList")) {
            getDiscListParams(builder);
        } else if (url.contains("lyric")) {
            lyricParams(builder);
        } else if (url.contains("fcg_myqq_toplist")) {
            fcg_myqq_toplistParams(builder);
        } else if (url.contains("fcg_v8_toplist_cp")) {
            topListParams(builder);
        } else if (url.contains("fcg_v8_singer_track_cp")) {
            songListParams(builder);
        } else if (url.contains("gethotkey.fcg")) {
            hotKeyParams(builder);
        } else if (url.contains("search_for_qq_cp")) {
            searchParams(builder);
        }
        return chain.proceed(request.newBuilder().url(builder.build()).build());
    }

    private void searchParams(HttpUrl.Builder builder) {
        builder.removeAllQueryParameters("needNewCode")
                .removeAllQueryParameters("platform")
                .addQueryParameter("needNewCode", "1")
                .addQueryParameter("platform", "h5")
                .addQueryParameter("uin", "0")
                .addQueryParameter("aggr", "0")
                .addQueryParameter("catZhida", "1")
                .addQueryParameter("zhidaqu", "1")
                .addQueryParameter("t", "0")
                .addQueryParameter("flag", "1")
                .addQueryParameter("sem", "1")
                .addQueryParameter("ie", "utf-8")
                .addQueryParameter("remoteplace", "txt.mqq.all")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

    private void hotKeyParams(HttpUrl.Builder builder) {
        builder.removeAllQueryParameters("needNewCode")
                .removeAllQueryParameters("platform")
                .addQueryParameter("needNewCode", "1")
                .addQueryParameter("platform", "h5")
                .addQueryParameter("uin", "0")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

    private void songListParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("order", "listen")
                .addQueryParameter("begin", "0")
                .addQueryParameter("num", "80")
                .addQueryParameter("songstatus", "1")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

    private void topListParams(HttpUrl.Builder builder) {
        builder.removeAllQueryParameters("platform")
                .removeAllQueryParameters("needNewCode")
                .addQueryParameter("needNewCode", "1")
                .addQueryParameter("platform", "h5")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK)
                .addQueryParameter("type", "top")
                .addQueryParameter("page", "detail")
                .addQueryParameter("tpl", "3")
                .addQueryParameter("uin", "0");
    }

    private void commonParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("g_tk", "1928093487")
                .addQueryParameter("inCharset", "utf-8")
                .addQueryParameter("outCharset", "utf-8")
                .addQueryParameter("notice", "0")
                .addQueryParameter("timeStamp2Date", "jsonp")
                .addQueryParameter("platform", "yqq")
                .addQueryParameter("needNewCode", "0")
                .addQueryParameter("hostUin", "0");

    }

    private void v8Params(HttpUrl.Builder builder) {
        builder.addQueryParameter("channel", "singer")
                .addQueryParameter("page", "list")
                .addQueryParameter("key", "all_all_all")
                .addQueryParameter("pagesize", "100")
                .addQueryParameter("pagenum", "1")
                .addQueryParameter("format", "jsonp")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

    private void getCdInfoParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("type", "1")
                .addQueryParameter("json", "1")
                .addQueryParameter("utf8", "1")
                .addQueryParameter("onlysong", "0");
    }

    private void getDiscListParams(HttpUrl.Builder builder) {
        builder.removeAllQueryParameters("timeStamp2Date")
                .addQueryParameter("timeStamp2Date", "json")
                .addQueryParameter("sin", "0")
                .addQueryParameter("ein", "29")
                .addQueryParameter("sortId", "5")
                .addQueryParameter("categoryId", "10000000")
                .addQueryParameter("rnd", "0.9889400562688042")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

    private void lyricParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("categoryId", "10000000")
                .addQueryParameter("pcachetime", "1513668010530");
    }

    private void fcg_myqq_toplistParams(HttpUrl.Builder builder) {
        builder.removeAllQueryParameters("platform")
                .removeAllQueryParameters("needNewCode")
                .addQueryParameter("platform", "h5")
                .addQueryParameter("needNewCode", "1")
                .addQueryParameter("uin", "0")
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

}
