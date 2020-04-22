package com.dad.sitemanage.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ScreenUtil {

    private ScreenUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取屏幕相关参数
     */
    public static String getScreenParams(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightPixels = getScreenHeight(activity);
        int widthPixels = getScreenWidth(activity);
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        int densityDpi = dm.densityDpi;
        float density = dm.density;
        float scaledDensity = dm.scaledDensity;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        float smallestWidthDP;
        if (widthDP < heightDP) {
            smallestWidthDP = widthDP;
        } else {
            smallestWidthDP = heightDP;
        }
        String str = "=== screen params ===";
        str += "\nheightPixels: " + heightPixels + "px";
        str += "\nwidthPixels: " + widthPixels + "px";
        str += "\nxdpi: " + xdpi + "dpi";
        str += "\nydpi: " + ydpi + "dpi";
        str += "\ndensityDpi: " + densityDpi + "dpi";
        str += "\ndensity: " + density;
        str += "\nscaledDensity: " + scaledDensity;
        str += "\nheightDP: " + heightDP + "dp";
        str += "\nwidthDP: " + widthDP + "dp";
        str += "\nsmallestWidthDP: " + smallestWidthDP + "dp";
        return str;
    }

    /**
     * 获取屏幕宽度
     * @param context Context
     * @return 屏幕宽度（px）
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度
     * @param context Context
     * @return 屏幕高度（px）
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取状态栏高度
     * @return
     */
    private static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String status_bar_height = clazz.getField("status_bar_height").get(object).toString();
            statusBarHeight = Integer.parseInt(status_bar_height);
            //dp--px
            statusBarHeight = activity.getResources().getDimensionPixelSize(statusBarHeight);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 判断是否有导航栏
     * @param activity
     * @return
     */
    private static boolean haveNavigation(Activity activity) {
        //屏幕的高度  真实物理的屏幕
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(displayMetrics);
        }

        int heightDisplay = displayMetrics.heightPixels;
        //为了防止横屏
        int widthDisplay = displayMetrics.widthPixels;
        DisplayMetrics contentDisplayMetrics = new DisplayMetrics();
        display.getMetrics(contentDisplayMetrics);

        int contentDisplay = contentDisplayMetrics.heightPixels;
        int contentDisplayWidth = contentDisplayMetrics.widthPixels;

        //屏幕内容高度  显示内容的屏幕
        int w = widthDisplay-contentDisplayWidth;
        //哪一方大于0   就有导航栏
        int h = heightDisplay-contentDisplay;
        return w > 0 || h > 0;
    }

    /**
     * 获取导航栏高度
     * @return
     */
    private static int getNavigationBarHeight(Activity activity) {
        int navigationBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String navigation_bar_height = clazz.getField("navigation_bar_height").get(object).toString();
            navigationBarHeight = Integer.parseInt(navigation_bar_height);
            //dp--px
            navigationBarHeight = activity.getResources().getDimensionPixelSize(navigationBarHeight);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return navigationBarHeight;
    }

}
