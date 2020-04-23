package com.dad.sitemanage.http.rx;


import android.text.TextUtils;

import com.dad.sitemanage.util.LogUtil;
import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;

/**
 * 结果统一处理
 * Created by Administrator on 2019/6/28.
 */
public abstract class ObserverImpl<T> implements Observer<String> {

    private static final String TAG = "ObserverImpl";

    @Override
    public void onNext(String response) {
        LogUtil.d(TAG,"response == " + response);
        if (!TextUtils.isEmpty(response)){
            Type geneticClass = getGeneticClass(this);
            T t = new Gson().fromJson(response, geneticClass);
            //接口重定向到handleResponse
            handleResponse(t);
        }
        onComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        onComplete();
        String error = throwable.getMessage();
        if (throwable instanceof UnknownHostException
                || throwable instanceof ConnectException
                || throwable instanceof NoRouteToHostException) {   //网络连接错误
            error = "网络连接错误";
        } else if (throwable instanceof SocketTimeoutException) {   //网络请求超时
            error = "网络请求超时";
        } else if (throwable instanceof IllegalStateException) {      //其他错误
            error = throwable.toString();
        }
        handleError(error);
    }

    private Type getGeneticClass(Object object){
        //获得带有泛型的直接父类的type
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        // ParameterizedType 带参数的类型 泛型
        //getActualTypeArguments 参数的类型 泛型类型
        return ((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
    }

    protected abstract void handleResponse(T t);

    protected abstract void handleError(String errorMsg);

}
