package com.tzly.ctcyh.cargo.license_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 驾照查分
 */
public class LicenseResultActivity extends AbstractBaseActivity {

    private LicenseResultFragment mFragment;
    private String score;

    protected boolean isCustomTitle() {
        return true;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(CargoGlobal.putExtra.license_score_extra)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                score = bundle.getString(CargoGlobal.putExtra.license_score_extra);
            }
        }
    }

    @Override
    protected void bindFragment() {
        //        titleContent("驾驶证查分");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = LicenseResultFragment.newInstance(score);
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

}