package com.yeguohao.music.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Inter implements Interceptor {
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("g_tk", "1928093487")
                .addHeader("inCharset", "utf-8")
                .addHeader("outCharset", "utf-8")
                .addHeader("notice", "0")
                .addHeader("format", "jsonp")
                .addHeader("platform", "h5")
                .addHeader("uin", "0")
                .addHeader("needNewCode", "1")
                .build();
        return chain.proceed(request);
    }
}
