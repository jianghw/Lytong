package com.zantong.mobilecttx.card.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.card.fragment.MyCardFragment;

import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 我的畅通卡 已有绑定卡页面
 */
public class MyCardActivity extends BaseJxActivity {

    private int mCurBottomPosition;
    private MyCardFragment mCouponListFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        mCurBottomPosition = 1;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("我的畅通卡");

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 1:
                if (mCouponListFragment == null) {
                    mCouponListFragment = MyCardFragment.newInstance();
                }
                FragmentUtils.replaceFragment(
                        fragmentManager, mCouponListFragment, R.id.lay_base_frame, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        mCouponListFragment = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }

}
