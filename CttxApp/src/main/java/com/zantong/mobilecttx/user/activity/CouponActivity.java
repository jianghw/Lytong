package com.zantong.mobilecttx.user.activity;

import android.support.v4.app.Fragment;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.common.adapter.TabListAdapter;
import com.zantong.mobilecttx.user.fragment.CouponFragment;
import com.zantong.mobilecttx.widght.PagingEnabledViewPager;
import com.zantong.mobilecttx.widght.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券
 */

public class CouponActivity extends MvpBaseActivity {

    SlidingTabLayout mCommonTab;
    PagingEnabledViewPager mCommonTabContent;

    private TabListAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.common_tab_list;
    }

    @Override
    protected void setTitleView() {
        mCommonTab = (SlidingTabLayout) findViewById(R.id.common_tab);
        mCommonTabContent = (PagingEnabledViewPager) findViewById(R.id.common_tab_content);

        setTitleText("我的优惠券");

        String titles[] = {"可使用的", "已失效的"};
        mAdapter = new TabListAdapter(
                getSupportFragmentManager(), getApplicationContext(), titles, fragmentList);
    }

    @Override
    protected void initMvPresenter() {
        fragmentList.add(CouponFragment.newInstance(1));
        fragmentList.add(CouponFragment.newInstance(2));

        mCommonTabContent.setAdapter(mAdapter);
        mCommonTabContent.setOffscreenPageLimit(fragmentList.size() - 1);
        //不可滑动
        mCommonTabContent.setPagingEnabled(false);

        mCommonTab.setDistributeEvenly(true);
        mCommonTab.setSelectedIndicatorColors(getResources().getColor(R.color.red));
        mCommonTab.setViewPager(mCommonTabContent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!fragmentList.isEmpty()) fragmentList.clear();
    }
}
