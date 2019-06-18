package com.zpf.rxjava.retrofit.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wpy on 2017/9/7.
 */

public class HeadRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("Connection","close")
                .method(original.method(), original.body())
                .build();
//                        .header("Authorization", AuthController.SingleTon.getInstance().getAuth())


        return chain.proceed(request);
    }
}
