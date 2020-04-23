package com.dad.sitemanage.ui.presenter;

import com.dad.sitemanage.base.BasePresenter;
import com.dad.sitemanage.ui.contract.IMainContract;

public class MainPresenter extends BasePresenter<IMainContract.View> implements IMainContract.Presenter {

    public MainPresenter(IMainContract.View view) {
        super(view);
    }

    @Override
    public void onDestroy() {

    }

}
