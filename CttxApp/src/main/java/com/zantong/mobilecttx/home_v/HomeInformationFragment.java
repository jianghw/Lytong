package com.zantong.mobilecttx.home_v;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tzly.ctcyh.java.response.news.IconsResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home_p.IHomeInfomationContract;

/**
 * 资讯页面页面
 */
public class HomeInformationFragment extends RefreshFragment
        implements IHomeInfomationContract.IHomeInfomationView {

    private IHomeInfomationContract.IHomeInfomationPresenter mPresenter;

    private RecyclerView mRvList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_infomation;
    }

    @Override
    protected void bindFragment(View fragment) {

    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getIcons();
        if (mPresenter != null) mPresenter.getNavigations();
    }

    @Override
    public void setPresenter(IHomeInfomationContract.IHomeInfomationPresenter presenter) {
        mPresenter = presenter;
    }

    public void initView(View view) {
        mRvList = (RecyclerView) view.findViewById(R.id.rv_list);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    @Override
    public void iconsError(String message) {
        
    }

    @Override
    public void iconsSucceed(IconsResponse result) {

    }
}
