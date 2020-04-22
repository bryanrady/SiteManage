package com.dad.sitemanage.core.http.interceptor;

import android.text.TextUtils;

import com.dad.sitemanage.util.LogUtil;
import com.dad.sitemanage.util.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: wangqingbin
 * @date: 2019/9/23 9:57
 */

public class SetCookieInterceptor implements Interceptor {

    private static final String TAG = "SetCookieInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        String cookie = (String) SPUtil.getParams("Cookie", "");
        LogUtil.d(TAG,"get cookie==" + cookie);
        if (!TextUtils.isEmpty(cookie)) {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Cookie", cookie)
                    .build();
            LogUtil.d(TAG,"add cookie==" + newRequest.headers().toString());
            return chain.proceed(newRequest);
        }
        return chain.proceed(chain.request());
    }

}
