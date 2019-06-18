package com.zpf.rxjava.retrofit.network.rx;


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


public abstract class RxSubscriber<T> implements Subscriber<T> {

    private String msg;


    @Override
    public void onSubscribe(Subscription s) {
        s.request(1);    //处理发送事件的数量 如果不写 则发送事件认为订阅者不能处理
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        msg = e.getMessage();



        _onError(msg);
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public void _onError(String msg) {
        Log.i("TAG", "==============================发生错误" + msg);    //发生错误的处理
    }
}


