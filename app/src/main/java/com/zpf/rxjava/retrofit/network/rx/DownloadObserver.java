package com.zpf.rxjava.retrofit.network.rx;

import android.app.Dialog;
import android.util.Log;

import com.zpf.rxjava.retrofit.network.download.DownloadManager;
import com.zpf.rxjava.retrofit.network.download.ProgressListener;
import com.zpf.rxjava.retrofit.network.exception.ApiException;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class DownloadObserver implements Observer<ResponseBody> {


    private String msg;   //错误的说明
    private Dialog mProgressDialog;
    private String fileName;

    public DownloadObserver(String fileName) {
        this.fileName = fileName;
    }

    public DownloadObserver(String fliename, Dialog progressDialog) {
        this.fileName = fliename;
        this.mProgressDialog = progressDialog;
    }

//    protected abstract void getDisposable(Disposable d);

    @Override
    public void onSubscribe(Disposable d) {
//        getDisposable(d);
    }


    @Override
    public void onNext(ResponseBody responseBody) {


        Observable
                .just(responseBody)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        try {
                            new DownloadManager().saveFile(responseBody, fileName, new ProgressListener() {
                                @Override
                                public void onResponseProgress(final long bytesRead, final long contentLength, final int progress, final boolean done, final String filePath) {
                                    Observable
                                            .just(progress)
                                            .distinctUntilChanged()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<Integer>() {
                                                @Override
                                                public void accept(Integer integer) throws Exception {
                                                    _onNext(bytesRead, contentLength, progress, done, filePath);
                                                }
                                            });
                                }

                            });

                        } catch (IOException e) {
                            msg = ApiException.handleException(e).getMessage();
                            _onError(msg);
                        }
                    }
                });


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

    public abstract void _onNext(long bytesRead, long contentLength, int progress, boolean done, String filePath);

    public abstract void _onError(String  msg);


    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}


