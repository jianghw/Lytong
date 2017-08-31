package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.weizhang.fragment.ViolationQueryFragment;

import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 违法查询页面 输入
 */
public class ViolationActivity extends BaseJxActivity {

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
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mVehicleLicenseBean =
                    intent.getExtras().getParcelable(JxGlobal.putExtra.car_item_bean_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("车辆违法查询");

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
                FragmentUtils.replaceFragment(
                        fragmentManager, mViolationQueryFragment, R.id.lay_base_frame, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        mViolationQueryFragment = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            closeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
