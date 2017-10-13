package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.browser.PayHtmlActivity;
import com.zantong.mobilecttx.browser.BrowserHtmlActivity;
import com.zantong.mobilecttx.browser.PayBrowserActivity;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.IOrderParentFtyContract;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule.activity.SparringActivity;
import com.zantong.mobilecttx.fahrschule.activity.SubjectActivity;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.order.fragment.MyOrderStatusFragment;
import com.zantong.mobilecttx.presenter.order.OrderParentPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.custom.popup.CustomDialog;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.imple.IPayTypeListener;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 订单列表页面
 */
public class MyOrderActivity extends BaseJxActivity
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
                if (mPresenter != null) mPresenter.getOrderList();
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
                Act.getInstance().gotoIntent(MyOrderActivity.this, OrderExpressActivity.class, bean.getOrderId());
            }

            /**
             * 预约服务
             */
            @Override
            public void doClickSubscribe(OrderListBean bean) {
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, bean.getGoodsName());
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, bean.getTargetUrl());
                Act.getInstance().gotoLoginByIntent(MyOrderActivity.this, BrowserHtmlActivity.class, intent);
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
    private void payTypeByUser(final OrderListBean orderListBean) {
        if (orderListBean.getCanPayType().contains("1")
                && orderListBean.getCanPayType().contains("2")) {

            CustomDialog.payTypeDialog(this, new IPayTypeListener() {
                @Override
                public void submitPayType(boolean type) {
                    if (type) gotoPayByCard(orderListBean);
                    else gotoPayByAlipay(orderListBean);
                }
            });
        } else if (orderListBean.getCanPayType().contains("1")) {
            gotoPayByCard(orderListBean);
        } else if (orderListBean.getCanPayType().contains("2")) {
            gotoPayByAlipay(orderListBean);
        }

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
        Intent intent = new Intent(this, PayBrowserActivity.class);
        intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付宝支付");
        intent.putExtra(JxGlobal.putExtra.web_url_extra, BuildConfig.CAR_MANGER_URL + "aliPay/aliPayHtml?orderId=" + bean.getOrderId());
        intent.putExtra(JxGlobal.putExtra.web_order_id_extra, bean.getOrderId());
        intent.putExtra(JxGlobal.putExtra.web_single_url_extra, true);
        startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
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

    /**
     * 刷新
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.getOrderList();
    }

    @Override
    protected void DestroyViewAndThing() {
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

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 加载订单失败状态
     */
    @Override
    public void getOrderListError(String message) {
        dismissLoadingDialog();
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
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void updateOrderStatusSucceed(BaseResponse result) {
        if (mPresenter != null) mPresenter.getOrderList();
    }

    @Override
    public void onPayOrderByCouponError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void onPayOrderByCouponSucceed(PayOrderResponse result) {
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.browser_title_extra, "支付");
        intent.putExtra(JxGlobal.putExtra.browser_url_extra, result.getData());
        Act.getInstance().gotoLoginByIntent(this, PayHtmlActivity.class, intent);
    }

    @Override
    public void getBankPayHtmlSucceed(PayOrderResponse result, String orderId) {
        Intent intent = new Intent(this, PayBrowserActivity.class);
        intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付");
        intent.putExtra(JxGlobal.putExtra.web_url_extra, result.getData());
        intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
        startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
    }

    /**
     * 页面回调
     * 1、驾校报名成功页面
     * 2、订单详情页面
     * 订单类型：1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_succeed) {

            Intent intent = new Intent();
            if (mOrderListBean.getType() == 3) {
                intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 2);
                Act.getInstance().gotoLoginByIntent(this, FahrschuleActivity.class, intent);
            } else if (mOrderListBean.getType() == 4) {
                intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 3);
                Act.getInstance().gotoLoginByIntent(this, SubjectActivity.class, intent);
            } else if (mOrderListBean.getType() == 5) {
                intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 2);
                Act.getInstance().gotoLoginByIntent(this, SparringActivity.class, intent);
            }

        } else if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
                && resultCode == JxGlobal.resultCode.web_order_id_error && data != null) {
            //前往 订单详情页面
            String orderId = data.getStringExtra(JxGlobal.putExtra.web_order_id_extra);
            String targetType = mOrderListBean.getTargetType();
            if (targetType.equals("0")) {//前往 订单详情页面
                Intent intent = new Intent(this, mOrderListBean.getType() == 6
                        ? AnnualDetailActivity.class : OrderDetailActivity.class);
                intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
                startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, mOrderListBean.getGoodsName());
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, mOrderListBean.getTargetUrl() + "?orderId=" + orderId);
                Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
            }
        }
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
