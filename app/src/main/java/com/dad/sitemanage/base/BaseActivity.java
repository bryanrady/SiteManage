package com.dad.sitemanage.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dad.sitemanage.util.ToastUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    private Unbinder mUnbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        setContentView(view);
        initView(view);
        mUnbinder = ButterKnife.bind(this);
        initPresenter();
    }

    public abstract int getLayoutId();

    public abstract void initView(View view);

    public abstract void initPresenter();

    private void initToolbar(){

    }

    @Override
    public void showToast(String message) {
        ToastUtil.showShortToast(this, message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hidLoading() {

    }

    @Override
    public void showNotNetworkLayout() {

    }

    @Override
    public void showNullDataLayout() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }
}
