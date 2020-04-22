package com.dad.sitemanage.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * add+show+hide模式下的懒加载
 * @param <P>
 */
public abstract class BaseLazyFragment<P extends BasePresenter> extends Fragment {

    //控制是否执行懒加载
    private boolean mIsLoaded = false;
    //判断Fragment是否是隐藏的
    private boolean mIsHidden = true;

    private Unbinder mUnbinder;
    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取Fragment之间传递过来的参数
        Bundle arguments = getArguments();
        if (arguments != null){
            initArguments(arguments);
        }
    }

    protected void initArguments(Bundle arguments) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
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
        mIsLoaded = true;
        judgeLazyLoad();
    }

    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    protected abstract void lazyLoad();

    private void judgeLazyLoad(){
        if (!mIsLoaded && !mIsHidden) {
            lazyLoad();
            mIsLoaded = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsLoaded = false;
        mIsHidden = true;
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }

}
