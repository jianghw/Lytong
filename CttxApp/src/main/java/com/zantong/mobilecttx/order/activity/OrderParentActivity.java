package com.zantong.mobilecttx.order.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.interf.IOrderParentFtyContract;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.order.fragment.OrderAllStatusFragment;
import com.zantong.mobilecttx.presenter.order.OrderParentPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * 订单列表页面
 */
public class OrderParentActivity extends BaseJxActivity
        implements IOrderParentFtyContract.IOrderParentFtyView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    List<Fragment> mFragmentList;
    private IOrderParentFtyContract.IOrderParentFtyPresenter mPresenter;

    private OrderAllStatusFragment orderAllStatusFragment = null;
    private OrderAllStatusFragment orderUnStatusFragment = null;
    private OrderAllStatusFragment orderCancleStatusFragment = null;
    private OrderAllStatusFragment orderPayStatusFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_order_parent;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("我的订单");
        
        OrderParentPresenter presenter = new OrderParentPresenter(
                Injection.provideRepository(getApplicationContext()), this);

        initView(view);
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();

        initViewPager();

        if (mPresenter != null) mPresenter.getOrderList();
    }

    public void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();

        orderAllStatusFragment = OrderAllStatusFragment.newInstance();
        orderAllStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderAllStatusFragment);

        orderUnStatusFragment = OrderAllStatusFragment.newInstance();
        orderUnStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderUnStatusFragment);

        orderCancleStatusFragment = OrderAllStatusFragment.newInstance();
        orderCancleStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderCancleStatusFragment);

        orderPayStatusFragment = OrderAllStatusFragment.newInstance();
        orderPayStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderPayStatusFragment);
    }

    @NonNull
    private RefreshListener getRefreshListener() {
        return new RefreshListener() {
            @Override
            public void refreshListData(int position) {
                if (mPresenter != null) mPresenter.getOrderList();
            }

            @Override
            public void doClickCancel(OrderListBean bean) {
                if (mPresenter != null) mPresenter.updateOrderStatus(bean);
            }

            @Override
            public void doClickPay(OrderListBean bean) {

            }
        };
    }

    private void initViewPager() {
        String[] title = new String[]{"全部订单", "待支付", "已取消", "已支付"};
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

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void DestroyViewAndThing() {
        orderAllStatusFragment = null;
        orderUnStatusFragment = null;
        orderCancleStatusFragment = null;
        orderPayStatusFragment = null;

        if (mPresenter != null) mPresenter.unSubscribe();
        if (mFragmentList != null) mFragmentList.clear();
    }

    @Override
    public void setPresenter(IOrderParentFtyContract.IOrderParentFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    @Override
    public void getOrderListError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void dataDistribution(String message) {
        ToastUtils.toastShort(message);
    }

    /**
     * 数据分发
     */
    @Override
    public void allPaymentData(List<OrderListBean> data) {
        if (orderAllStatusFragment != null)
            orderAllStatusFragment.setPayOrderListData(data);
    }

    @Override
    public void nonPaymentData(List<OrderListBean> orderList) {
        if (orderUnStatusFragment != null)
            orderUnStatusFragment.setPayOrderListData(orderList);
    }

    @Override
    public void havePaymentData(List<OrderListBean> orderList) {
        if (orderPayStatusFragment != null)
            orderPayStatusFragment.setPayOrderListData(orderList);
    }

    @Override
    public void cancelPaymentData(List<OrderListBean> orderList) {
        if (orderCancleStatusFragment != null)
            orderCancleStatusFragment.setPayOrderListData(orderList);
    }

    /**
     * 10.更新订单状态
     */
    @Override
    public void updateOrderStatusError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void updateOrderStatusSucceed(BaseResult result) {
        if (mPresenter != null) mPresenter.getOrderList();
    }


    public interface RefreshListener {

        void refreshListData(int position);

        void doClickCancel(OrderListBean bean);

        void doClickPay(OrderListBean bean);
    }
}
