package com.zpf.rxjava.retrofit;

import android.app.Application;

import com.zpf.rxjava.retrofit.network.HttpConfig;

/**
 * creat： zpf
 * mobile： 969038020@qq.com
 */
public class MyApplication extends Application {

    private static  MyApplication instence;

    @Override
    public void onCreate() {
        super.onCreate();
        instence = this;
        HttpConfig.init(this);
    }

    public static MyApplication getInstance() {
        return instence;

    }


}
