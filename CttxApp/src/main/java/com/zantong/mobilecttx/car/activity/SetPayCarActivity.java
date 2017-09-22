package com.zantong.mobilecttx.car.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.fragment.SetPayCarFragment;

import com.tzly.annual.base.util.ui.FragmentUtils;

public class SetPayCarActivity extends BaseJxActivity {

    private int mCurBottomPosition;
    private SetPayCarFragment mSetPayCarFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        mCurBottomPosition = 1;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_set_paycar;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("更改已绑定车辆");

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 1:
                if (mSetPayCarFragment == null) {
                    mSetPayCarFragment = SetPayCarFragment.newInstance();
                }
                FragmentUtils.replaceFragment(
                        fragmentManager, mSetPayCarFragment, R.id.activity_set_paycar_layout, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        mSetPayCarFragment = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }

}
