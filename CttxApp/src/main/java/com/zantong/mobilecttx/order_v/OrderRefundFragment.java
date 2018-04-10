package com.zantong.mobilecttx.order_v;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.order_p.IOrderRefundContract;
import com.zantong.mobilecttx.order_p.OrderRefundPresenter;

/**
 * 订单退款
 */
public class OrderRefundFragment extends RefreshFragment
        implements IOrderRefundContract.IOrderRefundView, View.OnClickListener {

    private static final String ORDER_ID = "order_id";
    /**
     * 请输入退款理由(必填)
     */
    private EditText mEditDetailedAddress;
    /**
     * 提    交
     */
    private Button mBtnQuery;
    private IOrderRefundContract.IOrderRefundPresenter mPresenter;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_order_refund;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        OrderRefundPresenter presenter = new OrderRefundPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
    }

    @Override
    protected void responseData(Object response) {
    }

    public static OrderRefundFragment newInstance(String mOrderId) {
        OrderRefundFragment fragment = new OrderRefundFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, mOrderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initView(View view) {
        mEditDetailedAddress = (EditText) view.findViewById(R.id.edit_detailed_address);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                verificationSubmitData();
                break;
            default:
                break;
        }
    }

    private void verificationSubmitData() {
        String name = getRemark();
        if (TextUtils.isEmpty(name)) {
            toastShort("请填写退款理由");
            return;
        }
        if (mPresenter != null) mPresenter.info();
    }

    @Override
    public void setPresenter(IOrderRefundContract.IOrderRefundPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getChannel() {
        return "2";
    }

    @Override
    public String getOrderId() {
        return getArguments().getString(ORDER_ID);
    }

    @Override
    public String getRemark() {
        return mEditDetailedAddress.getText().toString().trim();
    }

    @Override
    public void infoError(String message) {
        toastShort(message);
    }

    @Override
    public void infoSucceed(OrderRefundResponse result) {

    }
}