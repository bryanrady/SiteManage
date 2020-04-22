package com.dad.sitemanage.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.dad.sitemanage.SiteApplication;

public class AppUtil {

    /**
     * 获取应用程序版本名称
     * @return
     */
    public static String getAppVersionName() {
        try {
            PackageManager manager = SiteApplication.getContext().getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(SiteApplication.getContext().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本号
     * @return
     */
    public static int getAppVersionCode() {
        try {
            PackageManager manager = SiteApplication.getContext().getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(SiteApplication.getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
