package com.tzly.ctcyh.cargo.refuel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 加油分享
 */
public class OilShareActivity extends AbstractBaseActivity {

    private OilShareFragment mFragment;

    private String json;
    private String img;
    private String banner;
    private String type = null;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && intent.hasExtra(CargoGlobal.putExtra.oil_share_banner_extra)) {
                banner = bundle.getString(CargoGlobal.putExtra.oil_share_banner_extra);
            }
            if (bundle != null && intent.hasExtra(CargoGlobal.putExtra.oil_share_img_extra)) {
                img = bundle.getString(CargoGlobal.putExtra.oil_share_img_extra);
            }
            if (bundle != null && intent.hasExtra(CargoGlobal.putExtra.oil_share_json_extra)) {
                json = bundle.getString(CargoGlobal.putExtra.oil_share_json_extra);
            }
            if (bundle != null && intent.hasExtra(CargoGlobal.putExtra.oil_share_type_extra)) {
                type = bundle.getString(CargoGlobal.putExtra.oil_share_type_extra);
            }
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("分享");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            if (TextUtils.isEmpty(type)) {
                mFragment = OilShareFragment.newInstance(banner, img, json);
            } else {
                mFragment = OilShareFragment.newInstance(type);
            }
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }


}