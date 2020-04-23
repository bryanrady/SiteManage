package com.dad.sitemanage.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;

import com.dad.sitemanage.R;
import com.dad.sitemanage.base.BaseActivity;
import com.dad.sitemanage.ui.contract.IMainContract;
import com.dad.sitemanage.ui.fragment.InvoiceFragment;
import com.dad.sitemanage.ui.fragment.MyFragment;
import com.dad.sitemanage.ui.fragment.SignForFragment;
import com.dad.sitemanage.ui.presenter.MainPresenter;
import com.squareup.haha.perflib.Main;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.View {

    private InvoiceFragment mInvoiceFragment;
    private SignForFragment mSignForFragment;
    private MyFragment mMyFragment;

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        setDefaultFragment();
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void doBusiness() {

    }

    @OnClick({})
    void onClick(View view){
        switch (view.getId()){
            case 1:
                if (mInvoiceFragment == null){
                    mInvoiceFragment = new InvoiceFragment();
                }
                showHideFragment(0, mInvoiceFragment);
                break;
            case 2:
                if (mSignForFragment == null){
                    mSignForFragment = new SignForFragment();
                }
                showHideFragment(0, mSignForFragment);
                break;
            case 3:
                if (mMyFragment == null){
                    mMyFragment = new MyFragment();
                }
                showHideFragment(0, mMyFragment);
                break;
        }
    }

    private void setDefaultFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mInvoiceFragment = new InvoiceFragment();
        transaction.add(0, mInvoiceFragment);
        transaction.commit();
    }

    private void showHideFragment(int containerViewId, Fragment showFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        transaction.add(containerViewId, showFragment);
        for (Fragment fragment : fragments){
            if (fragment != showFragment){
                transaction.hide(fragment);
            }
        }
        transaction.show(showFragment);
        transaction.commit();
    }

}
