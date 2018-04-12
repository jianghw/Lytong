package com.zantong.mobilecttx.order_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;

/**
 * 订单退款
 */
public class OrderRefundActivity extends AbstractBaseActivity {

    private OrderRefundFragment mFragment;
    private String mOrderId;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(MainGlobal.putExtra.web_order_id_extra)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) mOrderId = bundle.getString(MainGlobal.putExtra.web_order_id_extra);
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("退款");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = OrderRefundFragment.newInstance(mOrderId);
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

}