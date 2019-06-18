package com.zpf.rxjava.retrofit.network;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import com.zpf.rxjava.retrofit.network.cookie.CookieJarImpl;
import com.zpf.rxjava.retrofit.network.cookie.store.SPCookieStore;
import com.zpf.rxjava.retrofit.network.interceptor.HeadRequestInterceptor;
import com.zpf.rxjava.retrofit.network.interceptor.LogginInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by wpy on 2017/7/22.
 */

public class HttpConfig {

    /**
     * 缓存路径
     */
    private static final String CachePath = Environment.getExternalStorageDirectory().getPath() + "/wenhuahaidian";

    /**
     * 缓存大小
     */
    private static final long CacheSize = 1024 * 1024 * 100;


    /**
     * 证书配置
     */
    private static SSLUtils.SSLParams sslParams = null;

    private static Context mcontext;


    public static void init(Context context) {
        mcontext = context;


//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        //设置缓存目录, 优先存储在sd卡
//        File cacheFile = new File(context.getCacheDir(), "network");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//
//
//        OkHttpClient client;
//        if (BuildConfig.BUILD_TYPE.equals("release")){
//            client = getTrusClient(new Buffer()
//                    .writeUtf8(CER)
//                    .inputStream());
//        }else {
//            client = builder
//                    //设置一下整体的超时
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(30, TimeUnit.SECONDS)
////                .cache(cache)
//                    .retryOnConnectionFailure(false)
//                    .addInterceptor(new HeadRequestInterceptor())
////                .addNetworkInterceptor(new StethoInterceptor())
//                    //日志打印
//                    .addInterceptor(new LogginInterceptor())
//                    .build();
//        }

        GRetrofit.init();
    }

    public static OkHttpClient getNormalClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder
                //设置一下整体的超时
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
//                .cache(cache)
                .retryOnConnectionFailure(false)
                //日志打印
                .addInterceptor(new LogginInterceptor(mcontext))
//                .addNetworkInterceptor(new NetCacheInterceptor())
                .build();

        return client;
    }

    /**
     * 对外提供的获取支持自签名的okhttp客户端
     */
    public static OkHttpClient getTrusClient() {

        //配置证书
//        SslConfig();


        return new OkHttpClient.Builder()

                //全局超时配置
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                // 配置证书
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .cookieJar(new CookieJarImpl(new SPCookieStore(mcontext)))
//                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                //  错误是否重试
                .retryOnConnectionFailure(true)

//                .addNetworkInterceptor(new NetCacheInterceptor(mcontext))
                //配置缓存l
//                .cache(new Cache(new File(CachePath), CacheSize))
                .addInterceptor(new HeadRequestInterceptor())
                .addInterceptor(new LogginInterceptor(mcontext))
                .build();
    }


    /**
     * 证书配置
     */
    private static void SslConfig() {


        InputStream cerInputStream = null;
        InputStream bksInputStream = null;
        try {
            cerInputStream = mcontext.getAssets().open("");   //放在asset目录下  filename  你的cre的名字记得d带后缀
            bksInputStream = mcontext.getAssets().open("");   //放在asset目录下  filename  你的bks  记得带后缀
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (null == cerInputStream) {
            //信任所有证书,不安全有风险
            sslParams = SSLUtils.getSslSocketFactory();
        } else {
            if (null != bksInputStream && !TextUtils.isEmpty("")) {                 //第二个参数为证书密码
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                sslParams = SSLUtils.getSslSocketFactory(bksInputStream, "", cerInputStream);   //第二个参数为证书密码
            } else {
                //使用预埋证书，校验服务端证书（自签名证书）
                sslParams = SSLUtils.getSslSocketFactory(cerInputStream);
            }
        }

    }

}
