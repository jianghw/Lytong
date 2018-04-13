package com.zantong.mobilecttx.violation_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;

/**
 * 违法查询页面 输入
 */
public class ViolationActivity extends AbstractBaseActivity {

    /**
     * 违法查询
     */
    private ViolationQueryFragment queryFragment = null;

    /**
     * 用于编辑或删除的标记
     */
    private VehicleLicenseBean mVehicleLicenseBean;


    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && intent.hasExtra(MainGlobal.putExtra.car_item_bean_extra))
                mVehicleLicenseBean = bundle.getParcelable(MainGlobal.putExtra.car_item_bean_extra);
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("车辆违法查询");

        titleServer();
    }

    @Override
    protected void imageClickListener() {
        MainRouter.gotoWebHtmlActivity(this, "客服",
                "http://h5.liyingtong.com/mot/faq/weizhang.html");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //车辆违法查询
        if (queryFragment == null) {
            queryFragment = mVehicleLicenseBean == null
                    ? ViolationQueryFragment.newInstance()
                    : ViolationQueryFragment.newInstance(mVehicleLicenseBean);
        }
        FragmentUtils.add(fragmentManager, queryFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        queryFragment = null;
    }
}
