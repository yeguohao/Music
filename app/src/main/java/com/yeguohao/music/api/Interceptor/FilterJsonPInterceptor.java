package com.yeguohao.music.api.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FilterJsonPInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String jsonPCallBack = request.url().queryParameter("jsonpCallback");
        if (jsonPCallBack != null) {
            Response response = chain.proceed(request);
            ResponseBody body = response.body();
            String result = body.string();

            result = result.replace(jsonPCallBack + "(", "").replace(")", "");

            ResponseBody realBody = ResponseBody.create(body.contentType(), result);
            return response.newBuilder().body(realBody).build();
        }
        return chain.proceed(request);
    }
}
