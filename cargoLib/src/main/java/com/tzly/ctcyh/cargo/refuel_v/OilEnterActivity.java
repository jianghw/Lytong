package com.tzly.ctcyh.cargo.refuel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;

/**
 * 加油进入
 */
public class OilEnterActivity extends AbstractBaseActivity {

    private OilEnterFragment mFragment;

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
        titleContent("88折加油");

        titleServer();
    }

    @Override
    protected void imageClickListener() {
        CargoRouter.gotoHtmlActivity(this, "客服系统",
                "http://h5.liyingtong.com/mot/faq/recharge.html");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = OilEnterFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragment != null) mFragment = null;
    }

}