package com.zantong.mobilecttx.order_v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.RefreshFragment;
import com.zantong.mobilecttx.R;

/**
 * 修改订单信息
 */
public class AmendOrderFragment extends RefreshFragment implements View.OnClickListener {

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

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_amend_order;
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

    public static AmendOrderFragment newInstance() {
        return new AmendOrderFragment();
    }

    public void initView(View view) {
        mEdtName = (EditText) view.findViewById(R.id.edt_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mTvArea = (TextView) view.findViewById(R.id.tv_area);
        mLayArea = (RelativeLayout) view.findViewById(R.id.lay_area);
        mEditDetailedAddress = (EditText) view.findViewById(R.id.edit_detailed_address);
        mImgTime = (ImageView) view.findViewById(R.id.img_time);
        mTvTime = (TextView) view.findViewById(R.id.tv_time);
        mLayTime = (RelativeLayout) view.findViewById(R.id.lay_time);
        mEditRemark = (EditText) view.findViewById(R.id.edit_remark);
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