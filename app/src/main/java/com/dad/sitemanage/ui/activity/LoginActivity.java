package com.dad.sitemanage.ui.activity;

import com.dad.sitemanage.R;
import com.dad.sitemanage.base.BaseActivity;
import com.dad.sitemanage.ui.contract.ILoginContract;
import com.dad.sitemanage.ui.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void doBusiness() {
        mPresenter.login("007158","123456");
    }

    @Override
    public void loginSuccess() {

    }

}
