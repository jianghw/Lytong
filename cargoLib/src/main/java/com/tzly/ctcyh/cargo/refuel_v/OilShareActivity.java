package com.tzly.ctcyh.cargo.refuel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 加油分享
 */
public class OilShareActivity extends AbstractBaseActivity {

    private OilShareFragment mFragment;
    private int configId;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && intent.hasExtra(CargoGlobal.putExtra.oil_share_extra)) {
                configId = bundle.getInt(CargoGlobal.putExtra.oil_share_extra);
            }
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("加油分享");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = OilShareFragment.newInstance(configId);
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }


}