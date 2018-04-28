package com.zantong.mobilecttx.home_v;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.bean.ModuleBannerBean;
import com.zantong.mobilecttx.base.bean.ModuleBannerResponse;
import com.zantong.mobilecttx.home_p.HomeInfomationPresenter;
import com.zantong.mobilecttx.home_p.IHomeInfomationContract;
import com.zantong.mobilecttx.home_p.ModuleBannerAdapter;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯页面页面
 */
public class HomeInformationFragment extends RefreshFragment
        implements IHomeInfomationContract.IHomeInfomationView, IRouterStatisticsId {

    private IHomeInfomationContract.IHomeInfomationPresenter mPresenter;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private RecyclerView mRecyclerList;
    private ModuleBannerAdapter adapterList;

    List<Fragment> mFragmentList;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragmentList != null) mFragmentList.clear();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_infomation;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        HomeInfomationPresenter presenter = new HomeInfomationPresenter(
                Injection.provideRepository(Utils.getContext()), this);
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
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mRecyclerList = (RecyclerView) view.findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void iconsError(String message) {

    }

    @Override
    public void iconsSucceed(ModuleBannerResponse result) {
        if (adapterList == null && mRecyclerList != null) {
            adapterList = new ModuleBannerAdapter(HomeInformationFragment.this, 5);
            if (mRecyclerList.getVisibility() != View.VISIBLE) {
                mRecyclerList.setVisibility(View.VISIBLE);
            }
            mRecyclerList.setAdapter(adapterList);
        }
        if (adapterList != null) {
            adapterList.replace(result.getData());
        } else {
            ToastUtils.toastShort("控件加载出错");
        }
    }

    public static HomeInformationFragment newInstance() {
        return new HomeInformationFragment();
    }

    @Override
    public void gotoByStatistId(String url, String title, int statisticsId) {
        RouterUtils.gotoByStatistId(url, title, String.valueOf(statisticsId), getActivity());
    }

    /**
     * 导航
     */
    @Override
    public void navigationError(String message) {

    }

    @Override
    public void navigationSucceed(ModuleBannerResponse result) {
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        List<ModuleBannerBean> list = result.getData();

        int len = list.size();
        String[] title = new String[len];
        for (int i = 0; i < len; i++) {
            title[i] = list.get(i).getTitle();
            mFragmentList.add(InformationFragment.newInstance(list.get(i).getTargetPath()));
        }

        OrderFragmentAdapter fragmentAdapter = new OrderFragmentAdapter(
                getChildFragmentManager(), mFragmentList, title);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
