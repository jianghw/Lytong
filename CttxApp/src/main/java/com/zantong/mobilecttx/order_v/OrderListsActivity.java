package com.zantong.mobilecttx.order_v;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.IOrderParentFtyContract;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.presenter.order.OrderParentPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表页面
 */
public class OrderListsActivity extends AbstractBaseActivity
        implements IOrderParentFtyContract.IOrderParentFtyView, IOrderListsItem {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    List<Fragment> mFragmentList;
    private IOrderParentFtyContract.IOrderParentFtyPresenter mPresenter;

    private OrderListsFragment mOrderListsFragment = null;
    private OrderListsFragment orderUnStatusFragment = null;
    private OrderListsFragment orderCancleStatusFragment = null;
    private OrderListsFragment orderPayStatusFragment = null;

    private volatile int act_pager = 1;

    @Override
    protected int initContentView() {
        return R.layout.activity_order_parent;
    }

    @Override
    protected void bundleIntent(Intent intent) {
    }

    @Override
    protected void bindFragment() {
        titleContent("我的订单");

        OrderParentPresenter presenter = new OrderParentPresenter(
                Injection.provideRepository(Utils.getContext()), this);

        initView();

        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();

        initViewPager();
    }

    @Override
    protected void initContentData() {
        if (mPresenter != null) mPresenter.getOrderList(act_pager);
    }

    public void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();

        mOrderListsFragment = OrderListsFragment.newInstance();
        mFragmentList.add(mOrderListsFragment);

        orderUnStatusFragment = OrderListsFragment.newInstance();
        mFragmentList.add(orderUnStatusFragment);

        orderCancleStatusFragment = OrderListsFragment.newInstance();
        mFragmentList.add(orderCancleStatusFragment);

        orderPayStatusFragment = OrderListsFragment.newInstance();
        mFragmentList.add(orderPayStatusFragment);
    }

    /**
     * 0--待支付
     * 1--已支付
     * 2--已取消
     * 3--进行中
     * 4--完成
     */
    private void initViewPager() {
        String[] title = new String[]{"全部订单", "待支付", "已取消", "已支付"};
        OrderFragmentAdapter fragmentAdapter = new OrderFragmentAdapter(
                getSupportFragmentManager(), mFragmentList, title);
        mViewPager.setAdapter(fragmentAdapter);
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
    protected void onDestroy() {
        super.onDestroy();

        mOrderListsFragment = null;
        orderUnStatusFragment = null;
        orderCancleStatusFragment = null;
        orderPayStatusFragment = null;

        if (mFragmentList != null) mFragmentList.clear();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(IOrderParentFtyContract.IOrderParentFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void toastError(String message) {
        ToastUtils.toastShort(message);
    }

    /**
     * 加载订单失败状态
     */
    @Override
    public void getOrderListError(String message) {
        toastError(message);

        if (mOrderListsFragment != null) mOrderListsFragment.setPayOrderListData(null);
        if (orderUnStatusFragment != null) orderUnStatusFragment.setPayOrderListData(null);
        if (orderCancleStatusFragment != null) orderCancleStatusFragment.setPayOrderListData(null);
        if (orderPayStatusFragment != null) orderPayStatusFragment.setPayOrderListData(null);
    }

    @Override
    public void dataDistribution(String message, int orderStatus) {
        toastError(message);

        if (orderStatus == 0) {
            if (orderUnStatusFragment != null)
                orderUnStatusFragment.setPayOrderListData(null);
        } else if (orderStatus == 2) {
            if (orderCancleStatusFragment != null)
                orderCancleStatusFragment.setPayOrderListData(null);
        } else {
            if (orderPayStatusFragment != null)
                orderPayStatusFragment.setPayOrderListData(null);
        }
    }

    /**
     * 数据分发
     */
    @Override
    public void allPaymentData(List<OrderListBean> data, int pager) {
        act_pager = pager + 1;

        if (mOrderListsFragment != null) {
            mOrderListsFragment.setPayOrderListData(data);
        }
    }

    @Override
    public void nonPaymentData(List<OrderListBean> orderList, int page) {
        if (orderUnStatusFragment != null) {
            orderUnStatusFragment.setPayOrderListData(orderList);
        }
    }

    @Override
    public void havePaymentData(List<OrderListBean> orderList, int page) {
        if (orderPayStatusFragment != null) {
            orderPayStatusFragment.setPayOrderListData(orderList);
        }
    }

    @Override
    public void cancelPaymentData(List<OrderListBean> orderList, int page) {
        if (orderCancleStatusFragment != null) {
            orderCancleStatusFragment.setPayOrderListData(orderList);
        }
    }

    /**
     * 10.更新订单状态
     */
    @Override
    public void updateOrderStatusError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void updateOrderStatusSucceed(BaseResponse result) {
        refreshFirstData();
    }

    @Override
    public void onPayOrderByCouponError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void onPayOrderByCouponSucceed(PayOrderResponse result) {
    }

    @Override
    public void getBankPayHtmlSucceed(PayOrderResponse result, String orderId) {
    }

    /**
     * 页面回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //支付类型页面2240 html 快递
        refreshFirstData();
    }

    private void refreshFirstData() {
        if (mOrderListsFragment != null) mOrderListsFragment.loadingFirstData();
        if (orderUnStatusFragment != null) orderUnStatusFragment.loadingFirstData();
        if (orderPayStatusFragment != null) orderPayStatusFragment.loadingFirstData();
        if (orderCancleStatusFragment != null) orderCancleStatusFragment.loadingFirstData();

        act_pager = 1;
        initContentData();
    }

    @Override
    public void refreshListData() {
        refreshFirstData();
    }

    @Override
    public void loadMoreData() {
        initContentData();
    }

    /**
     * 取消
     */
    @Override
    public void doClickCancel(OrderListBean bean) {
        if (mPresenter != null) mPresenter.cancelOrder(bean);
    }

    /**
     * 支付 订单类型：1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练
     */
    @Override
    public void doClickPay(OrderListBean bean) {
        MainRouter.gotoPayTypeActivity(this, bean.getOrderId());
    }

    /**
     * 自驾办理
     */
    @Override
    public void doClickDriving(OrderListBean bean) {
        MainRouter.gotoOilMapActivity(getApplicationContext());
    }

    /**
     * 呼叫快递
     */
    @Override
    public void doClickCourier(OrderListBean bean) {
        Act.getInstance().gotoIntent(
                OrderListsActivity.this, OrderExpressActivity.class, bean.getOrderId());
    }

    /**
     * 预约服务
     */
    @Override
    public void doClickSubscribe(OrderListBean bean) {
        MainRouter.gotoWebHtmlActivity(
                OrderListsActivity.this, bean.getGoodsName(), bean.getTargetUrl());
    }

    /**
     * 取消预约服务
     */
    @Override
    public void doClickUnSubscribe(OrderListBean bean) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + bean.getPhone());
        intent.setData(data);
        startActivity(intent);
    }

}
