package com.dad.sitemanage.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dad.sitemanage.R;
import com.dad.sitemanage.util.ToastUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    private RelativeLayout mToolbarLayout;
    private TextView mToolbarCenterTitle;
    private LinearLayout mLlToolbarBack;
    private Unbinder mUnbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        setContentView(view);
        mUnbinder = ButterKnife.bind(this);
        initLoading();
        initToolbar();
        initView();
        initPresenter();
        doBusiness();
    }

    public abstract int getLayoutId();

    protected void initView(){

    }

    private void initLoading() {
    }

    private void initToolbar(){
        mToolbarLayout = findViewById(R.id.rl_toolbar_layout);
        mToolbarCenterTitle = findViewById(R.id.tv_toolbar_center_title);
        mLlToolbarBack = findViewById(R.id.ll_toolbar_back);
        if (isShowBack()){
            if (mToolbarLayout != null){
                mLlToolbarBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                mLlToolbarBack.setVisibility(View.VISIBLE);
            }
        }
        String centerTitle = getCenterTitle();
        if (!TextUtils.isEmpty(centerTitle)){
            if (mToolbarLayout != null){
                mToolbarCenterTitle.setText(centerTitle);
            }
        }
    }

    public abstract void initPresenter();

    public abstract void doBusiness();

    protected String getCenterTitle(){
        return "";
    }

    protected boolean isShowBack(){
        return true;
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showShortToast(this, message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
