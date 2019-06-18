package com.zpf.rxjava.retrofit.network.response;

import java.io.Serializable;


/**
 * Created by wpy on 2017/7/23.
 * 接口通用数据
 */

public class BaseResponse<T> implements IResponse, Serializable {
    private String jsessionid;
    private int status;

    //
    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getStatus() {
//        return status;
//    }


    private T data;


//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }

//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }

    public T getData() {

        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 全局状态 根据返回状态判断是否请求成功
     *
     * @return
     */
    @Override
    public boolean isSuccess() {
        return true;
    }


}
