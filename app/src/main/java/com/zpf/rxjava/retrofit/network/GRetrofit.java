package com.zpf.rxjava.retrofit.network;

import android.app.Dialog;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import static com.example.zx.rxjavaretrofittest.network.HttpConfig.CER;

/**
 * Created by zpf on 2018/7/26.
 */

public class GRetrofit {
    private static Retrofit retrofit;
    private static Retrofit strRetrofit;

    private static Retrofit downloadretrofit;

    public static void init() {


        //可以根据实际项目需要  在Httpconfig里面添加相应的配置        因为打算开源  BaseURL 需要改变  不同接口不同的baseurl

        retrofit = new Retrofit.Builder()
                .client(HttpConfig.getTrusClient())
                .baseUrl(URLs.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // 下载的是String   不是对象
        strRetrofit = new Retrofit.Builder()
                .client(HttpConfig.getTrusClient())
                .baseUrl(URLs.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();


        //下载文件的retrofit 必须用这个
        downloadretrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.BASE_URL)
                .build();


    }

    public <T> T request(Class<T> service) {
        return retrofit.create(service);
    }

    public <T> T strrequest(Class<T> service) {
        return strRetrofit.create(service);
    }


    public <T> T downloadrequest(Class<T> service) {
        return downloadretrofit.create(service);
    }

    /**
     * 采用的是不背压的请求切换线程   不显示dialog
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> IoTomain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {

                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 带参数  显示loading对话框
     *
     * @param dialog loading
     * @param <T>    泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> IoTomain(final Dialog dialog) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if (dialog != null) {
                                    dialog.show();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 采用的是背压的请求切换线程
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> FlowIoTomain() {
        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
