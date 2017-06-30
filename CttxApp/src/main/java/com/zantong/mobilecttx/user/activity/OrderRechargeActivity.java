package com.zantong.mobilecttx.user.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.adapter.TabListAdapter;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.presenter.OrderPresenter;
import com.zantong.mobilecttx.user.fragment.OrderRechargeFragment;
import com.zantong.mobilecttx.interf.IOrderView;
import com.zantong.mobilecttx.widght.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class OrderRechargeActivity extends BaseMvpActivity<IOrderView,OrderPresenter> implements View.OnClickListener, IOrderView {

    @Bind(R.id.common_tab)
    SlidingTabLayout mCommonTab;
    @Bind(R.id.common_tab_content)
    ViewPager mCommonTabContent;

    private TabListAdapter mAdapter;
    private List<Fragment> fragmentList;

    @Override
    public void initView() {
        setTitleText("充值订单");
        fragmentList = new ArrayList<>();
//        fragmentList.add(new OrderRechargeFragment(0));
//        fragmentList.add(new OrderRechargeFragment(1));
//        fragmentList.add(new OrderRechargeFragment(2));
//        fragmentList.add(new OrderRechargeFragment(3));
        // 0：待支付；1：支付失败；2：已支付（钱支付）；3：退款中；4：退款成功；5：取消
        fragmentList.add(OrderRechargeFragment.newInstance(-1));
        fragmentList.add(OrderRechargeFragment.newInstance(0));
        fragmentList.add(OrderRechargeFragment.newInstance(2));
        fragmentList.add(OrderRechargeFragment.newInstance(3));
        String titles[] = {"全部", "待付款", "已付款", "退款中"};
        mAdapter = new TabListAdapter(getSupportFragmentManager(), this, titles, fragmentList);
        mCommonTabContent.setAdapter(mAdapter);
        mCommonTabContent.setOffscreenPageLimit(fragmentList.size());
        mCommonTab.setDistributeEvenly(true);
        mCommonTab.setSelectedIndicatorColors(getResources().getColor(R.color.red));
        mCommonTab.setViewPager(mCommonTabContent);
    }

    @Override
    public void initData() {

    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.common_tab_list;
    }

    @Override
    public void onClick(View v) {
//        presenter.login(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getOrderType() {
        return null;
    }


}
