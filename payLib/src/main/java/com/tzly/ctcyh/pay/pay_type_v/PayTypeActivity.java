package com.tzly.ctcyh.pay.pay_type_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FormatUtils;
import com.tzly.ctcyh.router.util.FragmentUtils;


/**
 * 支付方式 选择页面
 */

public class PayTypeActivity extends AbstractBaseActivity
        implements View.OnClickListener, IPayTypeUi {

    private PayTypeFragment mPayTypeFragment;
    private String mExtraOrder;
    private String mCurHost;

    /**
     * 金额:
     */
    private TextView mPriceTitle;
    /**
     * ￥
     */
    private TextView mPriceUnit;
    /**
     * 0.00
     */
    private TextView mTvSubmitPrice;
    /**
     * 支付
     */
    private TextView mTvPay;

    @Override
    protected int initContentView() {
        return R.layout.pay_activity_type_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(PayGlobal.putExtra.pay_type_order))
                    mExtraOrder = bundle.getString(PayGlobal.putExtra.pay_type_order);
                if (intent.hasExtra(PayGlobal.Host.pay_type_host))
                    mCurHost = bundle.getString(PayGlobal.Host.pay_type_host);
            }
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("选择支付");

        mPriceTitle = (TextView) findViewById(R.id.price_title);
        mPriceUnit = (TextView) findViewById(R.id.price_unit);
        mTvSubmitPrice = (TextView) findViewById(R.id.tv_submit_price);
        mTvPay = (TextView) findViewById(R.id.tv_pay);
        mTvPay.setOnClickListener(this);
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (TextUtils.isEmpty(mCurHost)) {
            if (mPayTypeFragment == null) {
                mPayTypeFragment = PayTypeFragment.newInstance(mExtraOrder);
                FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame);
            }
        } else if (mCurHost.equals(PayGlobal.Host.pay_type_host)) {
            if (mPayTypeFragment == null) {
                mPayTypeFragment = PayTypeFragment.newInstance(mExtraOrder);
                FragmentUtils.add(fragmentManager, mPayTypeFragment, R.id.lay_base_frame);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 页面回调code 注意
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPayTypeFragment = null;
    }

    /**
     * 支付
     */
    @Override
    public void onClick(View view) {
        if (mPayTypeFragment != null) mPayTypeFragment.submitPrice();
    }

    @Override
    public void setPayEnable(boolean b) {
        if (mTvPay != null) mTvPay.setEnabled(b);
    }

    @Override
    public String getSubmitPrice() {
        return mTvSubmitPrice.getText().toString().trim();
    }

    @Override
    public void setSubmitPrice(String price) {
        if (mTvSubmitPrice != null) mTvSubmitPrice.setText(price);
    }
}
