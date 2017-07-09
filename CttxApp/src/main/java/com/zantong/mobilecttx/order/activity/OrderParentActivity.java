package com.zantong.mobilecttx.order.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.order.fragment.OrderAllStatusFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面
 */
public class OrderParentActivity extends BaseJxActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    List<Fragment> mFragmentList;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_order_parent;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();

        initViewPager();
    }

    public void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();
        OrderAllStatusFragment orderAllStatusFragment = OrderAllStatusFragment.newInstance();


        mFragmentList.add(orderAllStatusFragment);
    }

    private void initViewPager() {
        String[] title = new String[]{"未支付", "已取消", "已支付"};
        OrderFragmentAdapter mainFragmentAdapter =
                new OrderFragmentAdapter(getSupportFragmentManager(), mFragmentList, title);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void DestroyViewAndThing() {

        if (mFragmentList != null) mFragmentList.clear();
    }

    public interface SwitcherListener {
        void setCurPosition(int position);
    }
}
