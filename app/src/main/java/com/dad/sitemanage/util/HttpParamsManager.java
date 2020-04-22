package com.dad.sitemanage.util;

import android.util.Log;

import com.dad.sitemanage.util.AppUtil;
import com.dad.sitemanage.util.DateUtil;
import com.dad.sitemanage.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpParamsManager {

    private static final String TAG = "HttpParamsManager";

    public static String getBaseParams(JSONObject jsonObject) {
        JSONObject obj;
        try {
            obj = new JSONObject();
            obj.put("msgId", DateUtil.getCurTimeFormat(DateUtil.FORMAT_1));
            obj.put("protocolVersion", AppUtil.getAppVersionCode());
            obj.put("osType", "1");
            obj.put("token", "AppUtil.getDeviceId()");
            obj.put("inData", jsonObject);
            LogUtil.d(TAG, obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return obj.toString();
    }

    /**
     * 用户登录
     * @param loginName
     * @param loginPassword
     * @return
     */
    public static JSONObject loginParams(String loginName, String loginPassword) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("loginName", loginName);
            jsonObject.put("loginPassword", loginPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
