package com.dad.sitemanage.base;

public interface IBaseView {

    void showToast(String message);

    void showLoading();

    void hideLoading();

    void showNotNetworkLayout();

    void showNullDataLayout();

}
