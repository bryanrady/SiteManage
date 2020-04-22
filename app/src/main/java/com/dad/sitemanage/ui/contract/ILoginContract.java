package com.dad.sitemanage.ui.contract;

import com.dad.sitemanage.base.IBaseView;
import com.dad.sitemanage.bean.User;

import io.reactivex.Observable;

public interface ILoginContract {

    interface View extends IBaseView {
        void loginSuccess();
    }

    interface Presenter{
        void login(String username, String password);
    }

    interface Model{
        Observable<String> login(String username, String password);
        void saveUserInfo(User user);
        String getUserName();
    }

}
