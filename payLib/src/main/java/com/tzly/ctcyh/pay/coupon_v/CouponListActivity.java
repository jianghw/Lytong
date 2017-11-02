package com.tzly.ctcyh.pay.coupon_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.jianghw.multi.state.layout.MultiState;
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
    private int mPayType;
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
            if (intent.hasExtra(PayGlobal.putExtra.web_pay_type_extra))
                mPayType = bundle.getInt(PayGlobal.putExtra.web_pay_type_extra);
            if (intent.hasExtra(PayGlobal.Host.coupon_list_host))
                mCurHost = bundle.getString(PayGlobal.Host.coupon_list_host);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.LOADING;
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
        //默认页面显示
        if (TextUtils.isEmpty(mCurHost)) {
            if (mCouponListFragment == null) {
                mCouponListFragment = CouponListFragment.newInstance(mExtraType,mPayType);
            }
            FragmentUtils.add(fragmentManager, mCouponListFragment,
                    R.id.lay_base_frame, false, true);
        } else if (mCurHost.equals(PayGlobal.Host.coupon_list_host)) {
            if (mCouponListFragment == null) {
                mCouponListFragment = CouponListFragment.newInstance(mExtraType,mPayType);
            }
            FragmentUtils.add(fragmentManager, mCouponListFragment,
                    R.id.lay_base_frame, false, true);
        }
    }

    /**
     * 手动刷新
     */
    @Override
    protected void userClickRefreshData() {
        if (mCouponListFragment != null) mCouponListFragment.onRefreshData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCouponListFragment = null;
    }
}
