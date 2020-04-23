package com.dad.sitemanage.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.widget.EditText;

import com.dad.sitemanage.R;
import com.dad.sitemanage.base.BaseActivity;
import com.dad.sitemanage.ui.contract.ILoginContract;
import com.dad.sitemanage.ui.presenter.LoginPresenter;
import com.dad.sitemanage.widget.LicensePlateView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginContract.View,
        LicensePlateView.OnKeyClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setTitle("自定义车牌键盘");

        setContentView(R.layout.activity_main);
        etPlate = findViewById(R.id.et_plate);
        LicensePlateView plateView = findViewById(R.id.plate_view);
        //禁止输入框弹出键盘
        etPlate.setInputType(InputType.TYPE_NULL);
        etPlate.setKeyListener(null);
        plateView.setOnKeyClickListener(this);
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

    private EditText etPlate;


    @Override
    public void onKeyClick(String key) {
        Editable editable = etPlate.getText();
        int start = etPlate.getSelectionStart();
        if (key.equalsIgnoreCase("Del")) {
            if (editable.length() > 0 && start > 0) {
                editable.delete(start - 1, start);
            }
            return;
        }
        editable.insert(start, key);
    }

}
