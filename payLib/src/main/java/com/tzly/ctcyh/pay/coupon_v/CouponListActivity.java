package com.tzly.ctcyh.pay.coupon_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;


/**
 * 优惠券 列表
 */
public class CouponListActivity extends JxBaseActivity {

    private CouponListFragment mCouponListFragment;
    /**
     * 页传递
     */
    private String mExtraType;
    private String mCurHost;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(PayGlobal.putExtra.coupon_list_type))
                mExtraType = bundle.getString(PayGlobal.putExtra.coupon_list_type);
            if (intent.hasExtra(PayGlobal.fragmentHost.coupon_list_host))
                mCurHost = bundle.getString(PayGlobal.fragmentHost.coupon_list_host);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("选择优惠劵");
    }

    @Override
    protected void initContentData() {
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurHost) {
            case PayGlobal.fragmentHost.coupon_list_host:
                if (mCouponListFragment == null) {
                    mCouponListFragment = CouponListFragment.newInstance(mExtraType);
                }
                FragmentUtils.add(fragmentManager, mCouponListFragment,
                        R.id.lay_base_frame, false, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCouponListFragment = null;
    }
}
