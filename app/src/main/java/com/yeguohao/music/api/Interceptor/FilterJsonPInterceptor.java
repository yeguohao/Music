package com.yeguohao.music.api.Interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FilterJsonPInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String jsonPCallBack = request.url().queryParameter("jsonpCallback");
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        assert body != null;

        String result = body.string();
        if (request.url().toString().contains("getDiscList")) {
            result = result.replace("\\", "");
            result = result.substring(1, result.length());
        }
        if (jsonPCallBack != null) {
            result = result.replace(jsonPCallBack + "(", "").replace(")", "");
            ResponseBody realBody = ResponseBody.create(body.contentType(), result);
            return response.newBuilder().body(realBody).build();
        }
        return chain.proceed(request);
    }

}
