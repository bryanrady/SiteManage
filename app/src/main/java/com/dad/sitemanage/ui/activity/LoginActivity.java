package com.dad.sitemanage.ui.activity;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.dad.sitemanage.R;
import com.dad.sitemanage.base.BaseActivity;
import com.dad.sitemanage.ui.contract.ILoginContract;
import com.dad.sitemanage.ui.presenter.LoginPresenter;
import com.dad.sitemanage.widget.LicensePlateView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginContract.View,
        LicensePlateView.OnKeyClickListener,LicensePlateView.OnButtonClickListener {

    @BindView(R.id.et_license_plate_province)
    EditText mEtPlateProvince;
    @BindView(R.id.et_license_plate_number)
    EditText mEtPlateNumber;
    @BindView(R.id.plate_view)
    LicensePlateView mPlateView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        //禁止输入框弹出键盘
        mEtPlateProvince.setInputType(InputType.TYPE_NULL);
        mEtPlateProvince.setKeyListener(null);
        mEtPlateNumber.setInputType(InputType.TYPE_NULL);
        mEtPlateNumber.setKeyListener(null);
        mPlateView.setOnKeyClickListener(this);
        mPlateView.setOnButtonClickListener(this);

        //感觉事件分发有问题,目前以这种方式来避免，只要控件接收到Touch事件，就强制调用onClick
        mEtPlateProvince.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                return true;
            }
        });
        mEtPlateNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                return true;
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void doBusiness() {
        mPresenter.login("007158","123456");
    }

    @Override
    public void loginSuccess() {

    }

    @OnClick({R.id.et_license_plate_province, R.id.et_license_plate_number})
    void doClick(View v){
        switch (v.getId()){
            case R.id.et_license_plate_province:
                if (mPlateView.isShow()){
                    if (mPlateView.getCurrentSourceType() == LicensePlateView.TYPE_PLATE_PROVINCE){
                        return;
                    }else{
                        mPlateView.dismiss();
                    }
                }
                String[] license_plate_province = getResources().getStringArray(R.array.license_plate_province);
                mPlateView.setSourceList(LicensePlateView.TYPE_PLATE_PROVINCE, license_plate_province);
                mPlateView.setSpanCount(7);
                mPlateView.show();
                break;
            case R.id.et_license_plate_number:
                if (mPlateView.isShow()){
                    if (mPlateView.getCurrentSourceType() == LicensePlateView.TYPE_PLATE_NUMBER){
                        return;
                    }else{
                        mPlateView.dismiss();
                    }
                }
                String[] license_plate_number = getResources().getStringArray(R.array.license_plate_number);
                mPlateView.setSourceList(LicensePlateView.TYPE_PLATE_NUMBER, license_plate_number);
                mPlateView.setSpanCount(9);
                mPlateView.show();
                break;
        }
    }

    @Override
    public void onKeyClick(int sourceType, String key) {
        switch (sourceType){
            case LicensePlateView.TYPE_PLATE_PROVINCE:
                handleKeyClick(mEtPlateProvince, key);
                break;
            case LicensePlateView.TYPE_PLATE_NUMBER:
                handleKeyClick(mEtPlateNumber, key);
                break;
        }
    }

    @Override
    public void onBtnClick(int sourceType, int btnType) {
        switch (sourceType){
            case LicensePlateView.TYPE_PLATE_PROVINCE:
                handleButtonClick(mEtPlateProvince, btnType);
                break;
            case LicensePlateView.TYPE_PLATE_NUMBER:
                handleButtonClick(mEtPlateNumber, btnType);
                break;
        }
    }

    private void handleKeyClick(EditText editText, String key){
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        editable.insert(start, key);
    }

    private void handleButtonClick(EditText editText, int btnType){
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        switch (btnType){
            case LicensePlateView.TYPE_CLEAR:
                editText.setText("");
                break;
            case LicensePlateView.TYPE_DELETE:
                if (editable.length() > 0 && start > 0) {
                    editable.delete(start - 1, start);
                }
                break;
            case LicensePlateView.TYPE_SURE:
                mPlateView.dismiss();
                break;
        }
    }
}
