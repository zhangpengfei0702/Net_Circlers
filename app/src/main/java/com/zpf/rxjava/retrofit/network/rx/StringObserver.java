package com.zpf.rxjava.retrofit.network.rx;

import android.app.Dialog;
import android.util.Log;

import com.zpf.rxjava.retrofit.network.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 需要请求返回的是String
 */
public abstract class StringObserver implements Observer<String> {


    private String msg;   //错误的说明
    private Dialog mProgressDialog;

    public StringObserver() {

    }

    public StringObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(String t) {

        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        dismissLoading();
        msg = ApiException.handleException(e).getMessage();
        _onError(msg);
    }

    @Override
    public void onComplete() {
        dismissLoading();
    }

    public abstract void _onNext(String t);
    public abstract void _onError(String t);

//    public void _onError(String msg) {
//        Log.i("TAG", "==============================发生错误" + msg);    //发生错误的处理
//    }


    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}


