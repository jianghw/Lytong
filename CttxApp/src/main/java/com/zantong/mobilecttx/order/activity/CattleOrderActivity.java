package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tzly.annual.base.bean.response.CattleOrderBean;
import com.tzly.annual.base.imple.CattleOrderListener;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.browser.HtmlBrowserActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.ICattleOrderContract;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule.activity.SparringActivity;
import com.zantong.mobilecttx.fahrschule.activity.SubjectActivity;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.order.fragment.CattleOrderStatusFragment;
import com.zantong.mobilecttx.presenter.order.CattleOrderPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

import com.tzly.annual.base.global.JxGlobal;

/**
 * 黄牛订单列表页面
 */
public class CattleOrderActivity extends BaseJxActivity
        implements ICattleOrderContract.ICattleOrderView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    List<Fragment> mFragmentList;
    private ICattleOrderContract.ICattleOrderPresenter mPresenter;
    /**
     * 全部
     */
    private CattleOrderStatusFragment allStatusFragment = null;
    /**
     * 已处理
     */
    private CattleOrderStatusFragment haveStatusFragment = null;
    /**
     * 资料审核中
     */
    private CattleOrderStatusFragment auditStatusFragment = null;
    /**
     * 办理中
     */
    private CattleOrderStatusFragment processStatusFragment = null;
    /**
     * 已完成
     */
    private CattleOrderStatusFragment completedStatusFragment = null;

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

        CattleOrderPresenter presenter = new CattleOrderPresenter(
                Injection.provideRepository(getApplicationContext()), this);

        initView(view);
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();

        initViewPager();

        if (mPresenter != null) mPresenter.queryOrderList();
    }

    @Override
    public void setPresenter(ICattleOrderContract.ICattleOrderPresenter presenter) {
        mPresenter = presenter;
    }

    public void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();

        allStatusFragment = CattleOrderStatusFragment.newInstance();
        allStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(allStatusFragment);

        haveStatusFragment = CattleOrderStatusFragment.newInstance();
        haveStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(haveStatusFragment);

        auditStatusFragment = CattleOrderStatusFragment.newInstance();
        auditStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(auditStatusFragment);

        processStatusFragment = CattleOrderStatusFragment.newInstance();
        processStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(processStatusFragment);

        completedStatusFragment = CattleOrderStatusFragment.newInstance();
        completedStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(completedStatusFragment);
    }

    @NonNull
    private CattleOrderListener getRefreshListener() {
        return new CattleOrderListener() {
            /**
             * 刷新
             */
            @Override
            public void refreshListData(int position) {
                if (mPresenter != null) mPresenter.queryOrderList();
            }

            @Override
            public void doClickHave(CattleOrderBean bean) {
            }

            @Override
            public void doClickAudit(CattleOrderBean bean) {
            }

            @Override
            public void doClickProcess(CattleOrderBean bean) {
            }

            @Override
            public void doClickCompleted(CattleOrderBean bean) {
            }
        };
    }

    /**
     * 0--待支付
     * 1--已支付
     * 2--已取消
     * 3--进行中
     * 4--完成
     */
    private void initViewPager() {
        String[] title = new String[]{"全部订单", "已接单", "资料审核中", "办理中", "已完成"};
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
        allStatusFragment = null;
        haveStatusFragment = null;
        auditStatusFragment = null;
        processStatusFragment = null;
        completedStatusFragment = null;

        if (mPresenter != null) mPresenter.unSubscribe();
        if (mFragmentList != null) mFragmentList.clear();
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
    public void queryOrderListError(String message) {
        if (allStatusFragment != null) allStatusFragment.setPayOrderListData(null);
        if (haveStatusFragment != null) haveStatusFragment.setPayOrderListData(null);
        if (auditStatusFragment != null) auditStatusFragment.setPayOrderListData(null);
        if (processStatusFragment != null) processStatusFragment.setPayOrderListData(null);
        if (completedStatusFragment != null) completedStatusFragment.setPayOrderListData(null);
    }

//    @Override
//    public void dataDistribution(String message, int orderStatus) {
//        ToastUtils.toastShort(message);
//
//        if (orderStatus == 0) {
//            if (haveStatusFragment != null)
//                haveStatusFragment.setPayOrderListData(null);
//        } else if (orderStatus == 2) {
//            if (auditStatusFragment != null)
//                auditStatusFragment.setPayOrderListData(null);
//        } else {
//            if (processStatusFragment != null)
//                processStatusFragment.setPayOrderListData(null);
//        }
//    }

    /**
     * 数据分发
     */
//    @Override
//    public void allPaymentData(List<OrderListBean> data) {
//        if (allStatusFragment != null)
//            allStatusFragment.setPayOrderListData(data);
//    }
//
//    @Override
//    public void nonPaymentData(List<OrderListBean> orderList) {
//        if (haveStatusFragment != null)
//            haveStatusFragment.setPayOrderListData(orderList);
//    }
//
//    @Override
//    public void havePaymentData(List<OrderListBean> orderList) {
//        if (processStatusFragment != null)
//            processStatusFragment.setPayOrderListData(orderList);
//    }
//
//    @Override
//    public void cancelPaymentData(List<OrderListBean> orderList) {
//        if (auditStatusFragment != null)
//            auditStatusFragment.setPayOrderListData(orderList);
//    }
//
//    /**
//     * 10.更新订单状态
//     */
//    @Override
//    public void updateOrderStatusError(String message) {
//        dismissLoadingDialog();
//        ToastUtils.toastShort(message);
//    }
//
//    @Override
//    public void updateOrderStatusSucceed(BaseResult result) {
//        if (mPresenter != null) mPresenter.getOrderList();
//    }
//
//    @Override
//    public void onPayOrderByCouponError(String message) {
//        dismissLoadingDialog();
//        ToastUtils.toastShort(message);
//    }
//
//    @Override
//    public void onPayOrderByCouponSucceed(PayOrderResult result) {
//        Intent intent = new Intent();
//        intent.putExtra(JxGlobal.putExtra.browser_title_extra, "支付");
//        intent.putExtra(JxGlobal.putExtra.browser_url_extra, result.getData());
//        Act.getInstance().gotoLoginByIntent(this, BrowserForPayActivity.class, intent);
//    }
//
//    @Override
//    public void getBankPayHtmlSucceed(PayOrderResult result, String orderId) {
//        Intent intent = new Intent(this, PayBrowserActivity.class);
//        intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付");
//        intent.putExtra(JxGlobal.putExtra.web_url_extra, result.getData());
//        intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
//        startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
//    }

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
                        ? AnnualOrderDetailActivity.class : OrderDetailActivity.class);
                intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
                startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, mOrderListBean.getGoodsName());
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, mOrderListBean.getTargetUrl() + "?orderId=" + orderId);
                Act.getInstance().gotoLoginByIntent(this, HtmlBrowserActivity.class, intent);
            }
        }
    }

}
