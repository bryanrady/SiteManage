package com.dad.sitemanage.ui.presenter;

import com.dad.sitemanage.base.BasePresenter;
import com.dad.sitemanage.base.IBaseView;
import com.dad.sitemanage.ui.contract.ILoginContract;
import com.dad.sitemanage.ui.model.LoginModel;

public class LoginPresenter extends BasePresenter implements ILoginContract.Presenter {

    private LoginModel mModel;

    public LoginPresenter(IBaseView view) {
        super(view);
        mModel = new LoginModel();
    }

    @Override
    public void onDestroy() {
        if (mModel != null){
            mModel.onDestroy();
        }
    }

}
