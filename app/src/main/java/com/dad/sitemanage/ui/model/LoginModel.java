package com.dad.sitemanage.ui.model;

import com.dad.sitemanage.base.BaseModel;
import com.dad.sitemanage.config.UrlConfig;
import com.dad.sitemanage.util.HttpParamsManager;
import com.dad.sitemanage.core.http.rx.RxRetrofitClient;
import com.dad.sitemanage.ui.contract.ILoginContract;
import com.dad.sitemanage.util.SPUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel extends BaseModel implements ILoginContract.Model {

    @Override
    public Observable<String> login(String username, String password) {
        return RxRetrofitClient.create()
                .url(UrlConfig.LOGIN_URL)
                .raw(HttpParamsManager.getBaseParams(HttpParamsManager.loginParams(username, password)))
                .build()
                .postRaw()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveUserInfo() {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public void onDestroy() {

    }
}
