package com.dad.sitemanage.base;

public interface IBaseView {

    void showToast(String message);

    void showLoading();

    void hidLoading();

    void showNotNetworkLayout();

    void showNullDataLayout();

}
