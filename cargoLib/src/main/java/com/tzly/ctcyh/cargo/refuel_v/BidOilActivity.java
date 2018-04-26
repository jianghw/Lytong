package com.tzly.ctcyh.cargo.refuel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 9.7加油申办
 */
public class BidOilActivity extends AbstractBaseActivity {

    private BidOilFragment mFragment;

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
        titleContent("申购9.7折加油卡");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = BidOilFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

}