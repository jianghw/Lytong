package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.IOrderParentFtyContract;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.order.fragment.MyOrderStatusFragment;
import com.zantong.mobilecttx.order_v.OrderExpressActivity;
import com.zantong.mobilecttx.presenter.order.OrderParentPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * 订单列表页面
 */
public class MyOrderActivity extends JxBaseActivity
        implements IOrderParentFtyContract.IOrderParentFtyView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    List<Fragment> mFragmentList;
    private IOrderParentFtyContract.IOrderParentFtyPresenter mPresenter;

    private MyOrderStatusFragment mMyOrderStatusFragment = null;
    private MyOrderStatusFragment orderUnStatusFragment = null;
    private MyOrderStatusFragment orderCancleStatusFragment = null;
    private MyOrderStatusFragment orderPayStatusFragment = null;
    /**
     * 支付类型 判断回调成功页面是哪个
     */

    private OrderListBean mOrderListBean;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {}

    @Override
    protected int initContentView() {
        return R.layout.activity_order_parent;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("我的订单");

        OrderParentPresenter presenter = new OrderParentPresenter(
                Injection.provideRepository(getApplicationContext()), this);

        initView(childView);
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();

        initViewPager();
    }

    @Override
    protected void initContentData() {
        if (mPresenter != null) mPresenter.getOrderList();
    }

    public void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();

        mMyOrderStatusFragment = MyOrderStatusFragment.newInstance();
        mMyOrderStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(mMyOrderStatusFragment);

        orderUnStatusFragment = MyOrderStatusFragment.newInstance();
        orderUnStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderUnStatusFragment);

        orderCancleStatusFragment = MyOrderStatusFragment.newInstance();
        orderCancleStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderCancleStatusFragment);

        orderPayStatusFragment = MyOrderStatusFragment.newInstance();
        orderPayStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderPayStatusFragment);
    }

    @NonNull
    private RefreshListener getRefreshListener() {
        return new RefreshListener() {
            /**
             * 刷新
             */
            @Override
            public void refreshListData(int position) {
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
                mOrderListBean = bean;

                payTypeByUser(bean);
            }

            /**
             * 自驾办理
             */
            @Override
            public void doClickDriving(OrderListBean bean) {
                Act.getInstance().gotoIntentLogin(MyOrderActivity.this, BaiduMapParentActivity.class);
            }

            /**
             * 呼叫快递
             */
            @Override
            public void doClickCourier(OrderListBean bean) {
                Act.getInstance().gotoIntent(
                        MyOrderActivity.this, OrderExpressActivity.class, bean.getOrderId());
            }

            /**
             * 预约服务
             */
            @Override
            public void doClickSubscribe(OrderListBean bean) {
                MainRouter.gotoHtmlActivity(
                        MyOrderActivity.this, bean.getGoodsName(), bean.getTargetUrl());
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
        };
    }

    /**
     * 支付类型选择
     * canPayType支付方式:1-工行支付,2-支付宝;例:1,2
     */
    private void payTypeByUser(OrderListBean orderListBean) {

        MainRouter.gotoPayTypeActivity(this, orderListBean.getOrderId());

        //        if (orderListBean.getCanPayType().contains("1")
        //                && orderListBean.getCanPayType().contains("2")) {
        //
        //            CustomDialog.payTypeDialog(this, new IPayTypeListener() {
        //                @Override
        //                public void submitPayType(boolean type) {
        //                    if (type) gotoPayByCard(orderListBean);
        //                    else gotoPayByAlipay(orderListBean);
        //                }
        //            });
        //        } else if (orderListBean.getCanPayType().contains("1")) {
        //            gotoPayByCard(orderListBean);
        //        } else if (orderListBean.getCanPayType().contains("2")) {
        //            gotoPayByAlipay(orderListBean);
        //        }

    }

    private void gotoPayByCard(OrderListBean bean) {
        String orderId = mOrderListBean.getOrderId();
        String payType = String.valueOf(mOrderListBean.getPayType());
        float orderPrice = mOrderListBean.getAmount();
        int price = (int) (orderPrice * 100);

        if (mPresenter != null && mOrderListBean.getType() == 1) {
            mPresenter.onPayOrderByCoupon(orderId, String.valueOf(price), payType);
        } else if (mPresenter != null) {
            mPresenter.getBankPayHtml(orderId, String.valueOf(price));
        }
    }

    /**
     * 支付宝支付
     */
    private void gotoPayByAlipay(OrderListBean bean) {
        //        Intent intent = new Intent(this, PayBrowserActivity.class);
        //        intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付宝支付");
        //        intent.putExtra(JxGlobal.putExtra.web_url_extra, BuildConfig.CAR_MANGER_URL + "aliPay/aliPayHtml?orderId=" + bean.getOrderId());
        //        intent.putExtra(JxGlobal.putExtra.web_order_id_extra, bean.getOrderId());
        //        intent.putExtra(JxGlobal.putExtra.web_single_url_extra, true);
        //        startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
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
        OrderFragmentAdapter mainFragmentAdapter =
                new OrderFragmentAdapter(getSupportFragmentManager(), mFragmentList, title);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMyOrderStatusFragment = null;
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

    /**
     * 加载订单失败状态
     */
    @Override
    public void getOrderListError(String message) {
        ToastUtils.toastShort(message);

        if (mMyOrderStatusFragment != null) mMyOrderStatusFragment.setPayOrderListData(null);
        if (orderUnStatusFragment != null) orderUnStatusFragment.setPayOrderListData(null);
        if (orderCancleStatusFragment != null) orderCancleStatusFragment.setPayOrderListData(null);
        if (orderPayStatusFragment != null) orderPayStatusFragment.setPayOrderListData(null);
    }

    @Override
    public void dataDistribution(String message, int orderStatus) {
        ToastUtils.toastShort(message);

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
    public void allPaymentData(List<OrderListBean> data) {
        if (mMyOrderStatusFragment != null)
            mMyOrderStatusFragment.setPayOrderListData(data);
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
        ToastUtils.toastShort(message);
    }

    @Override
    public void updateOrderStatusSucceed(BaseResponse result) {
        initContentData();
    }

    @Override
    public void onPayOrderByCouponError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void onPayOrderByCouponSucceed(PayOrderResponse result) {}

    @Override
    public void getBankPayHtmlSucceed(PayOrderResponse result, String orderId) {}

    /**
     * 页面回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //支付类型页面2240 html 快递
        //TODO 这么写好坑
        initContentData();
    }

    public interface RefreshListener {

        void refreshListData(int position);

        void doClickCancel(OrderListBean bean);

        void doClickPay(OrderListBean bean);

        void doClickDriving(OrderListBean bean);

        void doClickCourier(OrderListBean bean);

        void doClickSubscribe(OrderListBean bean);

        void doClickUnSubscribe(OrderListBean bean);
    }
}
