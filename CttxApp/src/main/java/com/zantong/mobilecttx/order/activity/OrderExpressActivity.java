package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.IOrderExpressContract;
import com.zantong.mobilecttx.presenter.order.OrderExpressPresenter;

import java.util.ArrayList;

import cn.qqtheme.framework.contract.bean.BaseResult;
import cn.qqtheme.framework.contract.custom.IAreaDialogListener;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.CustomDialog;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 呼叫快递
 */
public class OrderExpressActivity extends BaseJxActivity
        implements IOrderExpressContract.IOrderExpressView, View.OnClickListener {

    private IOrderExpressContract.IOrderExpressPresenter mPresenter;
    /**
     * 请输入姓名
     */
    private EditText mEdtName;
    /**
     * 请输入手机号
     */
    private EditText mEditPhone;
    private ImageView mImgBrand;
    /**
     * 请选择地区
     */
    private TextView mTvArea;
    private RelativeLayout mLayArea;
    /**
     * 请输入详细地址
     */
    private EditText mEditDetailedAddress;
    /**
     * 提     交
     */
    private Button mBtnQuery;
    /**
     * 订单号
     */
    private String mOrderId;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(JxGlobal.putExtra.common_extra)) {
            mOrderId = intent.getStringExtra(JxGlobal.putExtra.common_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_order_express;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("呼叫快递");

        initView();

        OrderExpressPresenter presenter = new OrderExpressPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(IOrderExpressContract.IOrderExpressPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 32.获取地区列表
     */
    @Override
    public void allAreasError(String message) {
        errorToast(message);
    }

    protected void errorToast(String message) {
        ToastUtils.toastShort(message);
        dismissLoadingDialog();
    }

    @Override
    public void allAreasSucceed(Object[] result) {

        ArrayList<String> firstList = new ArrayList<>();
        firstList.addAll((ArrayList<String>) result[0]);

        ArrayList<ArrayList<String>> secondList = new ArrayList<>();
        secondList.addAll((ArrayList<ArrayList<String>>) result[1]);

        ArrayList<ArrayList<ArrayList<String>>> thirdList = new ArrayList<>();
        thirdList.addAll((ArrayList<ArrayList<ArrayList<String>>>) result[2]);

        CustomDialog.popupBottomAllArea(this, firstList, secondList, thirdList, new IAreaDialogListener() {
            @Override
            public void setCurPosition(String position) {
                mTvArea.setText(position);
            }
        });
    }

    /**
     * 29.填写快递信息
     */
    @Override
    public void addExpressInfoError(String message) {
        errorToast(message);
    }

    @Override
    public void addExpressInfoSucceed(BaseResult result) {
        ToastUtils.toastShort("呼叫成功");
        finish();
    }

    @Override
    public String getUserName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public String getUserPhone() {
        return mEditPhone.getText().toString().trim();
    }

    @Override
    public String getAddress() {
        return mEditDetailedAddress.getText().toString().trim();
    }

    @Override
    public String getOrderId() {
        return mOrderId;
    }

    @Override
    public String getProvince() {
        return mTvArea.getText().toString().trim();
    }

    public void initView() {
        mEdtName = (EditText) findViewById(R.id.edt_name);
        mEditPhone = (EditText) findViewById(R.id.edit_phone);
        mImgBrand = (ImageView) findViewById(R.id.img_brand);
        mTvArea = (TextView) findViewById(R.id.tv_area);
        mLayArea = (RelativeLayout) findViewById(R.id.lay_area);
        mLayArea.setOnClickListener(this);
        mEditDetailedAddress = (EditText) findViewById(R.id.edit_detailed_address);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_area:
                if (mPresenter != null) mPresenter.getAllAreas();
                break;
            case R.id.btn_query:
                dataFormValidation();
                break;
            default:
                break;
        }
    }

    /**
     * 表单验证
     */
    public void dataFormValidation() {
        String name = getUserName();
        if (TextUtils.isEmpty(name) && name.length() < 1) {
            ToastUtils.toastShort("请输入姓名");
            return;
        }
        String phone = getUserPhone();
        if (TextUtils.isEmpty(phone) && phone.length() < 1 && !RegexUtils.isMobileSimple(phone)) {
            ToastUtils.toastShort("请输入正确手机");
            return;
        }
        String province = getProvince();
        if (TextUtils.isEmpty(province) && province.length() < 1) {
            ToastUtils.toastShort("请输入正确地区地址");
            return;
        }
        String address = getAddress();
        if (TextUtils.isEmpty(address) && address.length() < 1) {
            ToastUtils.toastShort("请输入正确详细地址");
            return;
        }

        if (mPresenter != null) mPresenter.addExpressInfo();
    }
}
