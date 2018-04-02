package com.zantong.mobilecttx.order_v;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tzly.ctcyh.router.base.RefreshFragment;
import com.zantong.mobilecttx.R;

/**
 * 订单退款
 */
public class OrderRefundFragment extends RefreshFragment implements View.OnClickListener {

    /**
     * 请输入退款理由(必填)
     */
    private EditText mEditDetailedAddress;
    /**
     * 提    交
     */
    private Button mBtnQuery;

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_order_refund;
    }

    @Override
    protected void bindFragment(View fragment) {

    }

    @Override
    protected void loadingFirstData() {

    }

    @Override
    protected void responseData(Object response) {

    }

    public static OrderRefundFragment newInstance() {
        return new OrderRefundFragment();
    }

    public void initView(View view) {
        mEditDetailedAddress = (EditText) view.findViewById(R.id.edit_detailed_address);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_query:
                break;
        }
    }
}