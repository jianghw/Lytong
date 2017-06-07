package com.zantong.mobilecttx.user.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.base.fragment.BaseFragment;
import com.zantong.mobilecttx.widght.MyViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Sandy
 */
@SuppressLint("ValidFragment")
public class MsgFragment extends BaseExtraFragment implements View.OnClickListener{


    private HomeFragmentPagerAdapter mAdapter;
    static TextView[] mTabTexts ;
    ArrayList<BaseFragment> mFragmentList;

    @Bind(R.id.mine_msg_viewpager)
    MyViewPager mViewPager;
    @SuppressLint("ValidFragment")
    public MsgFragment(TextView[] tabTexts){
        mTabTexts = tabTexts;
    }
//    public static MsgFragment newInstance(TextView[] tabTexts){
//
//        MsgFragment instance = new MsgFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("textviews", tabTexts);
//        instance.setArguments(args);
//
//        return instance;
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void initView(View view) {
//        mTabTexts = (TextView[])getArguments().getSerializable("textviews");
    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<BaseFragment>();
        mFragmentList.add(new MsgUserFragment());
        mFragmentList.add(new MsgSysFragment());

        mAdapter = new HomeFragmentPagerAdapter(this.getActivity().getSupportFragmentManager(), mFragmentList);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new PagerListener());
        for(TextView tv : mTabTexts)
            tv.setOnClickListener(this);
        setTabTextColor(0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mTabTexts[0]){
            mViewPager.setCurrentItem(0);
        }else{
            mViewPager.setCurrentItem(1);
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.mine_msg_fragment;
    }

    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

        ArrayList<BaseFragment> list;

        public HomeFragmentPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTexts[position].getText();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
    }

    class PagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            setTabTextColor(arg0);
        }

    }

    private void setTabTextColor(int index){
        for (int i = 0;i< mTabTexts.length;i++){
            mTabTexts[i].setSelected(false);
        }
        mTabTexts[index].setSelected(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
