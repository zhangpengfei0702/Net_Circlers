package com.zpf.rxjava.retrofit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.zpf.rxjava.retrofit.network.DebugLog
import com.zpf.rxjava.retrofit.network.GRetrofit
import com.zpf.rxjava.retrofit.network.api.GemService
import com.zpf.rxjava.retrofit.network.rx.RxErrorobserable
import com.zpf.rxjava.retrofit.network.rx.Rxobserable
import com.zpf.rxjava.retrofit.network.upload.UploadImg
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxView.clicks(get_url).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe {
                    geturl();

                }

        RxView.clicks(post_url).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe {
                    PostParam();

                }
        RxView.clicks(post_img).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe {
                    upLoadIng();

                }

    }


    /**
     * 普通get请求
     */
    fun geturl() {
        val url = "home/api/index/banner"
        GRetrofit().request(GemService::class.java).geturl(url)
                .compose(GRetrofit.IoTomain())
                .subscribe(object : RxErrorobserable<List<GetResponse>>() {

                    override fun _onNext(t: List<GetResponse>?) {

//                             请求成功处理
                    }

                    override fun _onError(t: String?) {
                                 //请求失败处理
                    }


                })

    }


    /**
     * 普通post请求
     */
    fun PostParam() {
        var map = HashMap<String, String>();
        map.put("Key", "value");

        GRetrofit().request(GemService::class.java).Post_Test("",map)
                .compose(GRetrofit.IoTomain())
                .subscribe(object : Rxobserable<PostResponse>() {

                    override fun _onNext(t: PostResponse?) {
                          //请求成功处理
                    }



                })

    }


    /**
     * 上传图片
     */
    fun upLoadIng(){


         val map = java.util.HashMap<String, String>()
         val url = ""
         val list = ArrayList<String>()
         val imglist = ArrayList<String>()

          //第一个参数 上传图片的url地址
//          第三个参数   为post请求 需要的其他参数
//          第四个参数 为图片地址的集合  单张也放进去集合里面
//          第五个参数  为多图上传的时候  键值的集合

         UploadImg.uploadImgsWithParams(url,  map, imglist,list).subscribe(object : Rxobserable<ResponseBody>() {
             override fun _onNext(t: ResponseBody?) {

                  //上传成功

             }

         })



     }









}
