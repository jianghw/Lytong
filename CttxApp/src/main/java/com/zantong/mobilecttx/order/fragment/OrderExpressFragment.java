package com.zantong.mobilecttx.order.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.IOrderExpressContract;
import com.zantong.mobilecttx.order.bean.ReceiveInfoBean;
import com.zantong.mobilecttx.order.bean.ReceiveInfoResult;
import com.zantong.mobilecttx.presenter.order.OrderExpressPresenter;

import java.util.ArrayList;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.contract.custom.IAreaDialogListener;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.CustomDialog;
import com.tzly.annual.base.util.RegexUtils;
import com.tzly.annual.base.util.ToastUtils;

/**
 * 呼叫快递页面
 */
public class OrderExpressFragment extends BaseRefreshJxFragment
        implements IOrderExpressContract.IOrderExpressView, View.OnClickListener {

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
     * 收件人
     */
    private TextView mTvReceiver;
    /**
     * 手机号
     */
    private TextView mTvPhone;
    /**
     * 地址
     */
    private TextView mTvAddress;
    /**
     * 订单号
     */
    private String mFirstCode;
    private String mSecondCode;

    private String mThirdCode;
    private static final String ARG_PARAM1 = "orderId";

    private IOrderExpressContract.IOrderExpressPresenter mPresenter;

    public static OrderExpressFragment newInstance(String orderId) {
        OrderExpressFragment fragment = new OrderExpressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.activity_order_express;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        OrderExpressPresenter presenter = new OrderExpressPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getReceiveInfo();
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
        if (result.length < 6) return;
        final ArrayList<String> first = (ArrayList<String>) result[3];
        final ArrayList<ArrayList<String>> second = (ArrayList<ArrayList<String>>) result[4];
        final ArrayList<ArrayList<ArrayList<String>>> third = (ArrayList<ArrayList<ArrayList<String>>>) result[5];

        final ArrayList<String> firstList = new ArrayList<>();
        firstList.addAll((ArrayList<String>) result[0]);
        final ArrayList<ArrayList<String>> secondList = new ArrayList<>();
        secondList.addAll((ArrayList<ArrayList<String>>) result[1]);
        final ArrayList<ArrayList<ArrayList<String>>> thirdList = new ArrayList<>();
        thirdList.addAll((ArrayList<ArrayList<ArrayList<String>>>) result[2]);

        CustomDialog.popupBottomAllArea(getActivity(), firstList, secondList, thirdList, new IAreaDialogListener() {
            @Override
            public void setCurPosition(String position) {
                String[] postions = position.split("/");
                int f = Integer.valueOf(postions[0]);
                int s = Integer.valueOf(postions[1]);
                int t = Integer.valueOf(postions[2]);
                mTvArea.setText(firstList.get(f) + "/" + secondList.get(f).get(s) + "/" + thirdList.get(f).get(s).get(t));

                mFirstCode = first.get(f);
                mSecondCode = second.get(f).get(s);
                mThirdCode = third.get(f).get(s).get(t);
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
        getActivity().finish();
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
        return getArguments().getString(ARG_PARAM1);
    }

    @Override
    public String getProvince() {
        return mFirstCode + "/" + mSecondCode + "/" + mThirdCode;
    }

    @Override
    public void getReceiveInfoError(String message) {
        errorToast(message);
    }

    @Override
    public void getReceiveInfoSucceed(ReceiveInfoResult result) {
        ReceiveInfoBean resultData = result.getData();
        mTvReceiver.setText(TextUtils.isEmpty(resultData.getReceiver()) ? "暂无" : resultData.getReceiver());
        mTvPhone.setText(TextUtils.isEmpty(resultData.getPhone()) ? "暂无" : resultData.getPhone());
        mTvAddress.setText(TextUtils.isEmpty(resultData.getAddress()) ? "暂无" : resultData.getAddress());
    }

    public void initView(View view) {
        mEdtName = (EditText) view.findViewById(R.id.edt_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mTvArea = (TextView) view.findViewById(R.id.tv_area);
        mLayArea = (RelativeLayout) view.findViewById(R.id.lay_area);
        mLayArea.setOnClickListener(this);
        mEditDetailedAddress = (EditText) view.findViewById(R.id.edit_detailed_address);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
        mTvReceiver = (TextView) view.findViewById(R.id.tv_receiver);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
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
        if (TextUtils.isEmpty(province) && province.length() < 1
                || TextUtils.isEmpty(mFirstCode)
                || TextUtils.isEmpty(mSecondCode) || TextUtils.isEmpty(mThirdCode)) {
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
