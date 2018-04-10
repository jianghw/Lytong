package com.zantong.mobilecttx.order_v;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.java.response.order.OrderInfoResponse;
import com.tzly.ctcyh.java.response.order.UpdateOrderResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.dialog.IOnDateSetListener;
import com.tzly.ctcyh.router.custom.dialog.PickUpDateDialogFragment;
import com.tzly.ctcyh.router.custom.popup.CustomDialog;
import com.tzly.ctcyh.router.imple.IAreaDialogListener;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.order_p.AmendOrderPresenter;
import com.zantong.mobilecttx.order_p.IAmendOrderContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 修改订单信息
 */
public class AmendOrderFragment extends RefreshFragment
        implements IAmendOrderContract.IAmendOrderView, View.OnClickListener {

    private static final String ORDER_ID = "order_id";
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
    private ImageView mImgTime;
    /**
     * 请选择时间
     */
    private TextView mTvTime;
    private RelativeLayout mLayTime;
    /**
     * 选填
     */
    private EditText mEditRemark;
    /**
     * 确认修改
     */
    private Button mBtnQuery;
    private IAmendOrderContract.IAmendOrderPresenter mPresenter;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_amend_order;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        AmendOrderPresenter presenter = new AmendOrderPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getUserOrderInfo();
    }

    @Override
    protected void responseData(Object response) {

    }

    public static AmendOrderFragment newInstance(String mOrderId) {
        AmendOrderFragment fragment = new AmendOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, mOrderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initView(View view) {
        mEdtName = (EditText) view.findViewById(R.id.edt_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mTvArea = (TextView) view.findViewById(R.id.tv_area);
        mLayArea = (RelativeLayout) view.findViewById(R.id.lay_area);
        mLayArea.setOnClickListener(this);
        mEditDetailedAddress = (EditText) view.findViewById(R.id.edit_detailed_address);
        mImgTime = (ImageView) view.findViewById(R.id.img_time);
        mTvTime = (TextView) view.findViewById(R.id.tv_time);
        mLayTime = (RelativeLayout) view.findViewById(R.id.lay_time);
        mLayTime.setOnClickListener(this);
        mEditRemark = (EditText) view.findViewById(R.id.edit_remark);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.btn_query) {
            verificationSubmitData();
        } else if (vId == R.id.lay_area) {
            if (mPresenter != null) mPresenter.getAllAreas();
        } else if (vId == R.id.lay_time) {
            PickUpDateDialogFragment dialogFragment = PickUpDateDialogFragment.newInstance();
            dialogFragment.setClickListener(new IOnDateSetListener() {
                @Override
                public void onDateSet(
                        DatePicker view, Date date, boolean usable) {
                    if (usable) return;
                    SimpleDateFormat format = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    mTvTime.setText(format.format(calendar.getTime()));
                }
            });
            dialogFragment.show(getFragmentManager(), "pickup_date");
        }
    }

    private void verificationSubmitData() {
        String name = getName();
        if (TextUtils.isEmpty(name)) {
            toastShort("请填写真实的姓名");
            return;
        }
        String phone = getPhone();
        if (TextUtils.isEmpty(phone)) {
            toastShort("请填写真实的手机");
            return;
        }
        String area = getAddress();
        if (TextUtils.isEmpty(area)) {
            toastShort("请填写真实的城市");
            return;
        }
        String address = getAddressDetail();
        if (TextUtils.isEmpty(address)) {
            toastShort("请填写真实的地址详情");
            return;
        }
        String date = getBespeakDate();
        if (TextUtils.isEmpty(date)) {
            toastShort("请填写取件时间");
            return;
        }
        if (mPresenter != null) mPresenter.updateOrderDetail();
    }

    @Override
    public void setPresenter(IAmendOrderContract.IAmendOrderPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public String getName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString().trim();
    }

    @Override
    public String getAddress() {
        return mTvArea.getText().toString().trim();
    }

    @Override
    public String getAddressDetail() {
        return mEditDetailedAddress.getText().toString().trim();
    }

    @Override
    public String getBespeakDate() {
        return mTvTime.getText().toString().trim();
    }

    @Override
    public String getSupplement() {
        return mEditRemark.getText().toString().trim();
    }

    @Override
    public String getOrderId() {
        return getArguments().getString(ORDER_ID);
    }

    @Override
    public void UserOrderInfoError(String message) {
        toastShort(message);
    }

    @Override
    public void UserOrderInfoSucceed(OrderInfoResponse result) {
        OrderInfoResponse.DataBean resultData = result.getData();
        if (resultData == null) return;

        try {
            mEdtName.setText(resultData.getName());
            mEditPhone.setText(resultData.getPhone());
            mTvArea.setText(resultData.getSheng() + "/" + resultData.getShi() + "/" + resultData.getXian());
            mEditDetailedAddress.setText(resultData.getAddressDetail());
            mEditRemark.setText(resultData.getSupplement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void allAreasSucceed(Object[] result) {
        if (result.length < 6) return;

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
            }
        });
    }

    @Override
    public void allAreasError(String message) {
        toastShort(message);
    }

    @Override
    public void updateOrderError(String message) {
        toastShort(message);
    }

    @Override
    public void updateOrderSucceed(UpdateOrderResponse result) {

    }
}