package com.dad.sitemanage.http.interceptor;

import android.text.TextUtils;

import com.dad.sitemanage.util.LogUtil;
import com.dad.sitemanage.util.SPUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;


public class SaveCookieInterceptor implements Interceptor {

    private static final String TAG = "SaveCookieInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        List<String> headers = response.headers("Set-Cookie");
        for (String header : headers) {
            if (header.contains(";")){
                String cookie = header.split(";")[0];
                LogUtil.d(TAG,"receive cookie==" + cookie);
                if (!TextUtils.isEmpty(cookie)) {
                    SPUtil.setParams("Cookie",cookie);
                }
            }
        }
        return response;
    }

}
