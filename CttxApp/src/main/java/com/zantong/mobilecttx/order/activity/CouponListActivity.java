package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;
import com.zantong.mobilecttx.order.fragment.CouponListFragment;

import java.util.ArrayList;

import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 优惠券 列表
 */

public class CouponListActivity extends BaseJxActivity {

    private int mCurBottomPosition;
    private CouponListFragment mCouponListFragment;
    /**
     * 加油优惠页传递
     */
    private ArrayList<RechargeCouponBean> rechargeCouponBeen;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            rechargeCouponBeen =
                    bundle.getParcelableArrayList(JxGlobal.putExtra.recharge_coupon_extra);
            if (rechargeCouponBeen != null) mCurBottomPosition = 1;
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("选择优惠劵");

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 1:
                if (mCouponListFragment == null) {
                    mCouponListFragment = CouponListFragment.newInstance(rechargeCouponBeen);
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
        if (rechargeCouponBeen != null) rechargeCouponBeen.clear();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return false;
    }
}
