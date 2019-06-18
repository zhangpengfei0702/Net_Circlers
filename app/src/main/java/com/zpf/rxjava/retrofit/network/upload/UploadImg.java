package com.zpf.rxjava.retrofit.network.upload;


import com.zpf.rxjava.retrofit.network.GRetrofit;
import com.zpf.rxjava.retrofit.network.api.GemService;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class UploadImg {


    /**
     * @param fileName  键值
     * @param map       //如果带参数的话 添加参数
     * @param filePaths 图片地址的集合
     *                  多图上传  键值不同的情况下
     * @return
     */
    public static Observable<ResponseBody> uploadImgsWithParams(String uploadUrl, Map<String, String> map, List<String> filePaths, List<String> filename) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != map) {
            for (String key : map.keySet()) {
                builder.addFormDataPart(key, (String) map.get(key));
            }
        }

        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //"medias"+i 后台接收图片流的参数名
            builder.addFormDataPart(filename.get(i), file.getName(), imageBody);
        }

        List<MultipartBody.Part> parts = builder.build().parts();

     return  new GRetrofit().request(GemService.class).uploadImgs(uploadUrl,parts).compose(GRetrofit.<ResponseBody>IoTomain());
    }










}
