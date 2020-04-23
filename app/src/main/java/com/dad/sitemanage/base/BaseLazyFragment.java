package com.dad.sitemanage.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLazyFragment<P extends BasePresenter> extends Fragment {

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

    protected abstract int getLayoutId();

    private void judgeLazyLoad(){
        if (!mIsLoaded && !mIsHidden) {
            initPresenter();
            lazyLoad();
            mIsLoaded = true;
        }
    }

    protected abstract void initPresenter();

    protected abstract void lazyLoad();

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
