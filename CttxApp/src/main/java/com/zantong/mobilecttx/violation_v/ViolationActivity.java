package com.zantong.mobilecttx.violation_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.global.MainGlobal;

/**
 * 违法查询页面 输入
 */
public class ViolationActivity extends JxBaseActivity {

    /**
     * 违法查询
     */
    private ViolationQueryFragment mViolationQueryFragment = null;
    /**
     * 页面指示器
     */
    private int mCurPosition;

    /**
     * 用于编辑或删除的标记
     */
    private VehicleLicenseBean mVehicleLicenseBean;


    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.putExtra.car_item_bean_extra))
                mVehicleLicenseBean = bundle.getParcelable(MainGlobal.putExtra.car_item_bean_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("车辆违法查询");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurPosition) {
            case 0://车辆违法查询
                if (mViolationQueryFragment == null) {
                    mViolationQueryFragment = (mVehicleLicenseBean == null)
                            ? ViolationQueryFragment.newInstance()
                            : ViolationQueryFragment.newInstance(mVehicleLicenseBean);
                }
                FragmentUtils.add(
                        fragmentManager, mViolationQueryFragment, R.id.lay_base_frame, false, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mViolationQueryFragment != null)
            mViolationQueryFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mViolationQueryFragment = null;
    }
}
