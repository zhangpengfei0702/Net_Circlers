package com.zpf.rxjava.retrofit.network.api;


import com.zpf.rxjava.retrofit.GetResponse;
import com.zpf.rxjava.retrofit.PostResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by zpf on 2018/7/26.
 */

public interface GemService {


    @GET
    Observable<List<GetResponse>> geturl(@Url String url);


    @FormUrlEncoded
    @POST()
    Observable<PostResponse> Post_Test(@Url String url, @FieldMap Map<String, String> files);


    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    /**
     * 图片上传
     *
     * @param uploadUrl
     * @param files
     * @return ResponseBody   可以替换为自己的实体类
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadImgs(@Url String uploadUrl,
                                        @Part List<MultipartBody.Part> files);
//


}
