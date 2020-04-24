package com.dad.sitemanage.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dad.sitemanage.R;
import com.dad.sitemanage.util.ToastUtil;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLazyFragment<P extends BasePresenter> extends Fragment implements IBaseView{

    private RelativeLayout mToolbarLayout;
    private TextView mToolbarCenterTitle;
    private LinearLayout mLlToolbarBack;
    //控制是否执行懒加载
    private boolean mIsLoaded = false;
    private boolean mIsHidden;

    private Unbinder mUnbinder;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        //获取Fragment之间传递过来的参数
        Bundle arguments = getArguments();
        if (arguments != null){
            initArguments(arguments);
        }
        mUnbinder = ButterKnife.bind(view);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        judgeLazyLoad();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mIsHidden = !hidden;
        judgeLazyLoad();
    }

    protected void initArguments(Bundle arguments) {

    }

    public abstract int getLayoutId();

    private void initView(View view){
        initLoading();
        initToolbar(view);
    }

    private void initLoading() {
    }

    private void initToolbar(View view){
        mToolbarLayout = view.findViewById(R.id.rl_toolbar_layout);
        mToolbarCenterTitle = view.findViewById(R.id.tv_toolbar_center_title);
        mLlToolbarBack = view.findViewById(R.id.ll_toolbar_back);
        if (isShowBack()){
            if (mToolbarLayout != null){
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

    private void judgeLazyLoad(){
        if (!mIsLoaded && !mIsHidden) {
            initPresenter();
            lazyLoad();
            mIsLoaded = true;
        }
    }

    public abstract void initPresenter();

    public abstract void lazyLoad();

    protected String getCenterTitle(){
        return "";
    }

    protected boolean isShowBack(){
        return true;
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showShortToast(getContext(), message);
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
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoaded = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }

}
