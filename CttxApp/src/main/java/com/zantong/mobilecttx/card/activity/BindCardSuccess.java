package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.fragment.BindCarSuccessFragment;
import com.zantong.mobilecttx.router.MainRouter;

public class BindCardSuccess extends AbstractBaseActivity {


    private BindCarSuccessFragment fragment;

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
    }

    @Override
    protected void bindFragment() {
        titleContent("绑定畅通卡");
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment == null) {
            fragment =  BindCarSuccessFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, fragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        MainRouter.gotoActiveActivity(this, 3);
        super.onDestroy();

        fragment = null;
    }
}
