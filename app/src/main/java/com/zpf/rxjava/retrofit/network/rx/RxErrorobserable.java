package com.zpf.rxjava.retrofit.network.rx;


import android.app.Dialog;
import android.util.Log;


import com.zpf.rxjava.retrofit.network.DebugLog;
import com.zpf.rxjava.retrofit.network.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class RxErrorobserable<T> implements Observer<T> {


    private String msg;   //错误的说明
    private Dialog mProgressDialog;

    public RxErrorobserable() {

    }

    public RxErrorobserable(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        dismissLoading();
        DebugLog.d("发生错误============" + e.getMessage());
        msg = ApiException.handleException(e).getMessage();
        _onError(msg);
    }

    @Override
    public void onComplete() {
        dismissLoading();
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String t);


    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}


