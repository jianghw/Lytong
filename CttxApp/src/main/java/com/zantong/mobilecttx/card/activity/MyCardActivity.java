package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.card.fragment.MyCardFragment;

/**
 * 我的畅通卡 已有绑定卡页面
 */
public class MyCardActivity extends AbstractBaseActivity {

    private int mCurBottomPosition;
    private MyCardFragment mCouponListFragment;


    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurBottomPosition) {
            case 1:
                if (mCouponListFragment == null) {
                    mCouponListFragment = MyCardFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mCouponListFragment, R.id.lay_base_frame);
                break;
            default:
                break;
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        mCurBottomPosition = 1;
    }

    @Override
    protected void bindFragment() {
        titleContent("我的畅通卡");

        titleMore("解绑");
    }

    @Override
    protected void rightClickListener() {
        if(mCouponListFragment!=null) mCouponListFragment.unBindCard();
    }


    @Override
    protected void initContentData() {
        initFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCouponListFragment = null;
    }
}
