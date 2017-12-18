package com.tzly.ctcyh.pay.coupon_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 优惠券详情页面
 */

public class CouponDetailActivity extends AbstractBaseActivity {

    private String mCouponId;
    private CouponDetailFragment mCouponDetailFragment;

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(PayGlobal.putExtra.coupon_detail_id))
                    mCouponId = bundle.getString(PayGlobal.putExtra.coupon_detail_id);
            }
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindFragment() {
        titleContent("券码详情");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mCouponDetailFragment == null) {
            mCouponDetailFragment = CouponDetailFragment.newInstance(mCouponId);
        }
        FragmentUtils.add(fragmentManager, mCouponDetailFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCouponDetailFragment = null;
    }
}