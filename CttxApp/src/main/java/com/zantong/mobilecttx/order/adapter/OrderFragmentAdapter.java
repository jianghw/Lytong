package com.zantong.mobilecttx.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jianghw on 17-7-9.
 * fragment adapter
 */

public class OrderFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> mDataList;

    public OrderFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public OrderFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] titles) {
        super(fm);
        this.mDataList = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null) {
            return super.getPageTitle(position);
        }
        return titles[position];
    }
}
