package com.dad.sitemanage.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends IBaseView>{

    private WeakReference<V> mRefView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V view){
        this.mRefView = new WeakReference<>(view);
    }

    protected V getView(){
        return mRefView != null ? mRefView.get() : null;
    }

    protected void addDispose(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void clearDispose(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    protected void requestStartBefore(){
        if (getView() != null){
            getView().showLoading();
        }
    }

    protected void requestEndAfter(){
        if (getView() != null){
            getView().hideLoading();
        }
    }

    protected void showErrorMessage(String errorMsg){
        if (getView() != null){
            getView().showToast(errorMsg);
        }
    }

    public abstract void onDestroy();

}
