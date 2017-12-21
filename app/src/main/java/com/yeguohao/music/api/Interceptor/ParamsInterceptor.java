package com.yeguohao.music.api.Interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamsInterceptor implements Interceptor {

    private static final String JSONP_CALLBACK = "jp34";

    @Override
    public Response intercept(Chain chain) throws IOException {
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
        }
        return chain.proceed(request.newBuilder().url(builder.build()).build());
    }

    private void commonParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("g_tk", "1928093487")
                .addQueryParameter("inCharset", "utf-8")
                .addQueryParameter("outCharset", "utf-8")
                .addQueryParameter("notice", "0")
                .addQueryParameter("format", "jsonp")
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
                .addQueryParameter("jsonpCallback", JSONP_CALLBACK);
    }

    private void getCdInfoParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("type", "1")
                .addQueryParameter("json", "1")
                .addQueryParameter("utf8", "1")
                .addQueryParameter("onlysong", "0");
    }

    private void getDiscListParams(HttpUrl.Builder builder) {
        builder.removeAllQueryParameters("format")
                .addQueryParameter("format", "json")
                .addQueryParameter("sin", "0")
                .addQueryParameter("ein", "29")
                .addQueryParameter("sortId", "5")
                .addQueryParameter("categoryId", "10000000")
                .addQueryParameter("rnd", "0.9889400562688042");
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
