package com.dad.sitemanage;

import android.app.Application;

import com.dad.sitemanage.config.UrlConfig;
import com.dad.sitemanage.core.http.RetrofitInit;

public class SiteApplication extends Application {

    private static SiteApplication mContext;
    private static boolean mIsDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initHttp();
    }

    public static SiteApplication getContext(){
        return mContext;
    }

    public static boolean isDebug(){
        return mIsDebug;
    }

    private void initHttp(){
        RetrofitInit.init(this)
                .withApiHost(UrlConfig.ROOT_URL)
                .configure();
    }

}
