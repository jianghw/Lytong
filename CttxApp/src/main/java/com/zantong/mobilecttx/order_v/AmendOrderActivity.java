package com.zantong.mobilecttx.order_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;

/**
 * 修改订单信息
 */
public class AmendOrderActivity extends AbstractBaseActivity {

    private AmendOrderFragment mFragment;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("修改信息");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = AmendOrderFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

}