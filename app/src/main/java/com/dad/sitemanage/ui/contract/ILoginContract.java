package com.dad.sitemanage.ui.contract;

import com.dad.sitemanage.base.IBaseView;

public interface ILoginContract {

    public interface View extends IBaseView {
        void loginSuccess();

        void loginFailed(String errorMsg);
    }

    public interface Presenter{

    }

}
