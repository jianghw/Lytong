package com.zantong.mobile.order.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.response.OrderListBean;
import com.tzly.annual.base.custom.dialog.CustomDialog;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.imple.CattleOrderListener;
import com.tzly.annual.base.imple.IEditTextDialogListener;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.browser.BrowserHtmlActivity;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.contract.ICattleOrderContract;
import com.zantong.mobile.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobile.fahrschule.activity.SparringActivity;
import com.zantong.mobile.fahrschule.activity.SubjectActivity;
import com.zantong.mobile.order.adapter.OrderFragmentAdapter;
import com.zantong.mobile.order.fragment.CattleOrderStatusFragment;
import com.zantong.mobile.presenter.order.CattleOrderPresenter;
import com.zantong.mobile.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

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

        if (mPresenter != null) mPresenter.getAnnualInspectionOrders();
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
                if (mPresenter != null) mPresenter.getAnnualInspectionOrders();
            }

            @Override
            public void doClickHave(OrderListBean bean) {
                submitRadioBtnData(bean);
            }

            @Override
            public void doClickAudit(OrderListBean bean) {
            }

            @Override
            public void doClickProcess(OrderListBean bean) {
                submitEditTextData(bean);
            }

            @Override
            public void doClickCompleted(OrderListBean bean) {
            }
        };
    }

    /**
     * 审核资料
     */
    private void submitRadioBtnData(final OrderListBean bean) {
        CustomDialog.radioBtnDialog(this, new IEditTextDialogListener() {
            @Override
            public void submitData(String trim) {
                if (mPresenter != null)
                    mPresenter.annualOrderTargetState(bean.getOrderId(), TextUtils.isEmpty(trim) ? "6" : "7", trim);
            }
        });
    }

    /**
     * 输入快递单号
     */
    private void submitEditTextData(final OrderListBean bean) {
        CustomDialog.editTextDialog(this, "请输入快递单号", new IEditTextDialogListener() {
            @Override
            public void submitData(String trim) {
                if (mPresenter != null)
                    mPresenter.addBackExpressInfo(bean.getOrderId(), trim);
            }
        });
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
    public void annualInspectionOrdersError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);

        if (allStatusFragment != null) allStatusFragment.setPayOrderListData(null);
        if (haveStatusFragment != null) haveStatusFragment.setPayOrderListData(null);
        if (auditStatusFragment != null) auditStatusFragment.setPayOrderListData(null);
        if (processStatusFragment != null) processStatusFragment.setPayOrderListData(null);
        if (completedStatusFragment != null) completedStatusFragment.setPayOrderListData(null);
    }

    @Override
    public void dataDistribution(String message, int position) {
        ToastUtils.toastShort(message);

        if (position == 1)
            haveStatusData(null);
        else if (position == 2)
            auditStatusData(null);
        else if (position == 3)
            processStatusData(null);
        else if (position == 4)
            completedStatusData(null);
    }

    /**
     * 数据分发
     */
    @Override
    public void allStatusData(List<OrderListBean> data) {
        if (allStatusFragment != null)
            allStatusFragment.setPayOrderListData(data);
    }

    @Override
    public void haveStatusData(List<OrderListBean> orderList) {
        if (haveStatusFragment != null)
            haveStatusFragment.setPayOrderListData(orderList);
    }

    @Override
    public void auditStatusData(List<OrderListBean> orderList) {
        if (auditStatusFragment != null)
            auditStatusFragment.setPayOrderListData(orderList);
    }

    @Override
    public void processStatusData(List<OrderListBean> orderList) {
        if (processStatusFragment != null)
            processStatusFragment.setPayOrderListData(orderList);
    }

    @Override
    public void completedStatusData(List<OrderListBean> orderList) {
        if (completedStatusFragment != null)
            completedStatusFragment.setPayOrderListData(orderList);
    }

    /**
     * 审核资料 失败
     */
    @Override
    public void annualOrderTargetStateError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void annualOrderTargetStateSucceed(BaseResult result) {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    Log.e("Exception when onBack", e.toString());
                }
            }
        }.start();

        if (mPresenter != null) mPresenter.getAnnualInspectionOrders();
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

}
