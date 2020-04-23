package com.dad.sitemanage.ui.presenter;

import com.dad.sitemanage.base.BasePresenter;
import com.dad.sitemanage.base.BaseResult;
import com.dad.sitemanage.bean.User;
import com.dad.sitemanage.http.rx.ObserverImpl;
import com.dad.sitemanage.ui.contract.ILoginContract;
import com.dad.sitemanage.ui.model.LoginModel;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends BasePresenter<ILoginContract.View> implements ILoginContract.Presenter {

    private LoginModel mModel;

    public LoginPresenter(ILoginContract.View view) {
        super(view);
        mModel = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        requestStartBefore();
        Observable<String> login = mModel.login(username, password);
        login.subscribe(new ObserverImpl<BaseResult<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDispose(d);
            }

            @Override
            public void onComplete() {
                requestEndAfter();
            }

            @Override
            protected void handleResponse(BaseResult<User> result) {
                getView().loginSuccess();
            }

            @Override
            protected void handleError(String errorMsg) {
                showErrorMessage(errorMsg);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mModel != null){
            mModel.onDestroy();
        }
    }

}
