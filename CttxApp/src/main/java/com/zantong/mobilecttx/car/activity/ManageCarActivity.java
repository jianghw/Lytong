package com.zantong.mobilecttx.car.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.fragment.ManageCarListFragment;
import com.zantong.mobilecttx.utils.jumptools.Act;

import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 车辆管理母页面
 */
public class ManageCarActivity extends BaseJxActivity {

    private int mCurBottomPosition;
    private ManageCarListFragment mCarListFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        mCurBottomPosition = 1;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_manage_car;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("车辆管理");

        TextView tvCar = (TextView) view.findViewById(R.id.tv_bind_car);
        tvCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act.getInstance().gotoIntent(ManageCarActivity.this, SetPayCarActivity.class);
            }
        });

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 1:
                if (mCarListFragment == null) {
                    mCarListFragment = ManageCarListFragment.newInstance();
                }
                FragmentUtils.replaceFragment(
                        fragmentManager, mCarListFragment, R.id.lay_base_frame, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        mCarListFragment = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }
}
