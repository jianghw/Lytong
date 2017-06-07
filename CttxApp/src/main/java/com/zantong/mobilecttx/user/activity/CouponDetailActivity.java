package com.zantong.mobilecttx.user.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.user.bean.CouponFragmentBean;

/**
 * 优惠券详情页面
 */

public class CouponDetailActivity extends MvpBaseActivity {

    private TextView mTvCouponTitle;
    private TextView mTvCouponCode;
    private TextView mTvCouponTime;
    private TextView mTvCouponContent;
    private TextView mTvCouponUse;

    @Override
    protected int getContentResId() {
        return R.layout.coupon_detail_activity;
    }

    private void assignViews() {
        mTvCouponTitle = (TextView) findViewById(R.id.tv_coupon_title);
        mTvCouponCode = (TextView) findViewById(R.id.tv_coupon_code);
        mTvCouponTime = (TextView) findViewById(R.id.tv_coupon_time);
        mTvCouponContent = (TextView) findViewById(R.id.tv_coupon_content);
        mTvCouponUse = (TextView) findViewById(R.id.tv_coupon_use);
    }

    @Override
    protected void setTitleView() {
        setTitleText("优惠券详情");

        assignViews();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            CouponFragmentBean bean = bundle.getParcelable("CouponFragmentBean");

            initCouponData(bean);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initCouponData(CouponFragmentBean bean) {
        if (bean == null) return;
        mTvCouponTitle.setText(bean.getCouponName());
        mTvCouponCode.setText(bean.getCouponCode() == null
                ? "暂时无优惠码" : bean.getCouponCode().equals("null")
                ? "暂时无优惠码" : bean.getCouponCode());
        mTvCouponTime.setText("到期时间：" + bean.getCouponValidityEnd());

        mTvCouponContent.setText(bean.getCouponContent());
        mTvCouponUse.setText(bean.getCouponUse());
    }

    @Override
    protected void initMvPresenter() {

    }
}