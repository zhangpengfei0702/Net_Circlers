package com.zpf.rxjava.retrofit.network.params;

import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpf.rxjava.retrofit.network.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by wpy on 2017/4/12.
 */

public class JsonParams implements Serializable {
    private JSONObject jb = new JSONObject();
    private HashMap<String, String> map = new HashMap<>();
    private String jStr = "";
    private static JsonParams JsonParams = null;

    /**
     * 构造函数私有化
     */
    private JsonParams() {
    }

    /**
     * 获取单利的对象
     *
     * @return
     */
    public static JsonParams getInstance() {
        if (JsonParams == null) {
            JsonParams = new JsonParams();
        }
        return JsonParams;
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     */
    public JsonParams addParam(String key, String value) {
        try {
            if (jb == null)
                jb = new JSONObject();
            jb.put(key, value);
            if (!TextUtils.isEmpty(jStr)) {
                jStr = jStr + ",";
            }
            jStr = jStr + ("\\\"" + key + "\\\":\\\"" + value + "\\\"");
            map.put(key, value);
        } catch (Exception e) {
        }
        return this;
    }

    public JsonParams addParam(String key, int value) {
        try {
            if (jb == null)
                jb = new JSONObject();
            jb.put(key, value);
            if (!TextUtils.isEmpty(jStr)) {
                jStr = jStr + ",";
            }
            jStr = jStr + ("\\\"" + key + "\\\":\\\"" + value + "\\\"");
            map.put(key, "" + value);
        } catch (Exception e) {
        }
        return this;
    }

    public JsonParams addParamJ(String key, List<JSONObject> values) {
        try {
            if (jb == null)
                jb = new JSONObject();
            JSONArray ja = new JSONArray();
            for (int i = 0; values != null && i < values.size(); i++) {
                if (values.get(i) != null) {
                    ja.put(values.get(i));
                }
            }
            jb.put(key, ja);
            map.put(key, ja.toString());
        } catch (Exception e) {
        }
        return this;
    }

    public JsonParams addParamJ(JSONObject values) {
        try {
            if (jb == null)
                jb = new JSONObject();
            jb = values;
//            map.put(key, ja.toString());
        } catch (Exception e) {
        }
        return this;
    }

    public JsonParams addParamS(String key, List<String> values) {
        try {
            if (jb == null)
                jb = new JSONObject();
            JSONArray ja = new JSONArray();
            for (int i = 0; values != null && i < values.size(); i++) {
                if (values.get(i) != null) {
                    ja.put(values.get(i));
                }
            }
            jb.put(key, ja);
            map.put(key, ja.toString());
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 构建出json字符串
     *
     * @return
     */
    public RequestBody build() {
        try {
            addCommonParams();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> reqMap = new HashMap<>();

            String reqStr = objectMapper.writeValueAsString(map);
            Log.i("TAG", "原始串build============================" + reqStr);

            RequestBody requestBody = RequestBody.create(Constant.MEDIA_TYPE_JSON, reqStr);
            return requestBody;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jb = null;
            map.clear();
        }
    }

    /**
     * 构建出json字符串
     *
     * @return
     */
    public RequestBody buildJ() {
        try {
            addCommonParams();
            Log.i("TAG", "原始串buildJ=================" + jb.toString());

            RequestBody requestBody = RequestBody.create(Constant.MEDIA_TYPE_JSON, jb.toString());
            return requestBody;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            map.clear();
            jb = null;
        }
    }


    /**
     * 添加通用参数
     */
    private void addCommonParams() throws JSONException {
        if (!CommonUtils.getLocale()) {
            jb.put(Constant.FIELD_LANGUAGE, "en-us");
            map.put(Constant.FIELD_LANGUAGE, "en-us");
        }
    }

}
