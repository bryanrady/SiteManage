package com.dad.sitemanage.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static Toast mToast = null;

    public static void showShortToast(Context context, String content){
        if(mToast == null){
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showLongToast(Context context, String content){
        if(mToast==null){
            mToast=Toast.makeText(context, content, Toast.LENGTH_LONG);
        }else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public static void showDurationToast(Context context, String content, int duration){
        if(mToast==null){
            mToast=Toast.makeText(context ,content, duration);
        }else {
            mToast.setText(content);
            mToast.setDuration(duration);
        }
        mToast.show();
    }


}
