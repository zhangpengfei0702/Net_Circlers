package com.zpf.rxjava.retrofit.network.interceptor;

import android.content.Context;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * creat： zpf
 * mobile： 969038020@qq.com
 */
public class NetCacheInterceptor implements Interceptor {


    private Context mcontext;


    public NetCacheInterceptor(Context context) {
        mcontext = context;

    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

//        if (NetworkUtil.isNetAvailable(mcontext)) {
//            //获取头部信息
//            String cacheControl = request.cacheControl().toString();
//            return response.newBuilder()
//                    //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", cacheControl)
//                    .header("Cache-Control", "max-age=60")
//                    .build();
//        }
        return response;


    }


}
