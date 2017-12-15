package com.tzly.ctcyh.pay.coupon_v;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.coupon_p.TabListAdapter;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.EnabledViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券状态
 */

public class CouponStatusActivity extends AbstractBaseActivity {

    private TabLayout mTabLayout;
    private EnabledViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    protected void bundleIntent(Intent intent) {}

    @Override
    protected int initContentView() {
        return R.layout.pay_activity_coupon_status;
    }

    @Override
    protected void bindFragment() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (EnabledViewPager) findViewById(R.id.viewPager);

        titleContent("我的优惠券");
    }

    @Override
    protected void initContentData() {
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        if (!mFragmentList.isEmpty()) mFragmentList.clear();

        String[] title = new String[]{"优惠券", "码券"};

        mFragmentList.add(CouponStatusFragment.newInstance());
        mFragmentList.add(CouponCodeFragment.newInstance());

        TabListAdapter mainFragmentAdapter =
                new TabListAdapter(getSupportFragmentManager(), mFragmentList, title);
        mViewPager.setAdapter(mainFragmentAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mViewPager.setPagingEnabled(false);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragmentList != null) mFragmentList.clear();
    }
}
