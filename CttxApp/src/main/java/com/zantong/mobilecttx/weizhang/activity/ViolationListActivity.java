package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.card.activity.UnblockedCardActivity;
import com.zantong.mobilecttx.contract.IViolationListFtyContract;
import com.zantong.mobilecttx.home.activity.Codequery;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.presenter.weizhang.ViolationListPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;
import com.zantong.mobilecttx.weizhang.fragment.ViolationListFragment;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.global.JxGlobal;

/**
 * 违法信息列表页面
 */
public class ViolationListActivity extends BaseJxActivity
        implements IViolationListFtyContract.IViolationListFtyView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    private ViolationListFragment orderAllStatusFragment = null;
    private ViolationListFragment orderUnStatusFragment = null;
    private ViolationListFragment orderPayStatusFragment = null;

    private IViolationListFtyContract.IViolationListFtyPresenter mPresenter;
    /**
     * 传值
     */
    private ViolationDTO mViolationDTO;
    private String mTitle;
    /**
     * 已绑定车辆
     */
    private List<PayCar> mPayCarList;
    /**
     * 是否获取已绑车辆 标记
     */
    private boolean mPayCarOk;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = (String) LoginData.getInstance().mHashMap.get("carnum");

            Bundle bundle = intent.getExtras();
            mViolationDTO = (ViolationDTO) bundle.getSerializable("params");
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_violation_list;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent(mTitle);

        ViolationListPresenter presenter = new ViolationListPresenter(
                Injection.provideRepository(getApplicationContext()), this);

        initView(view);
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();

        initViewPager();

        initFirstData();
    }

    public void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();

        orderAllStatusFragment = ViolationListFragment.newInstance();
        orderAllStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderAllStatusFragment);

        orderUnStatusFragment = ViolationListFragment.newInstance();
        orderUnStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderUnStatusFragment);

        orderPayStatusFragment = ViolationListFragment.newInstance();
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
                initFirstData();
            }

            /**
             * 支付
             */
            @Override
            public void doClickPay(ViolationBean bean) {
                doItemClickPay(bean);
            }
        };
    }

    protected void initFirstData() {
        if (mPresenter != null) mPresenter.getPayCars();
        if (mPresenter != null) mPresenter.searchViolation();
    }

    protected void doItemClickPay(ViolationBean bean) {
        String violationnum = bean.getViolationnum();
        int processste = bean.getProcessste();//处理状态

        if (!MainRouter.isUserLogin()) {
            MainRouter.gotoLoginActivity(this);
        } else if (processste == 2 || processste == 3) {
            showDialogToCodequery(bean);
        } else {
            goToPayFragment(bean, violationnum);
        }
    }

    /**
     * 付款fragment
     */
    private void goToPayFragment(ViolationBean bean, String violationnum) {
        String penaltyNum = violationnum.substring(6, 7);

        if ("1".equals(penaltyNum) || "2".equals(penaltyNum)) {//是否处罚决定书
            showPayFragment(bean);
        } else if (TextUtils.isEmpty(MainRouter.getUserFilenum())) {//未綁卡
            byCardHome();
        } else {
            if (!mPayCarOk) {
                ToastUtils.toastShort("获取已绑定车辆失败，请下拉刷新数据");
                return;
            }
            if (mPayCarList == null || mPayCarList.size() < 2) {
                showPayFragment(bean);
            } else {
                for (PayCar payCar : mPayCarList) {
                    if (payCar.getCarnum().equals(bean.getCarnum())) {
                        showPayFragment(bean);
                        return;
                    }
                }
                ToastUtils.toastLong("当前车辆为非绑定车辆，但你已绑定另2辆车，请先去车辆管理进行改绑");
            }
        }
    }

    /**
     * 支付弹出框
     */
    private void showPayFragment(ViolationBean bean) {
        Intent intent = new Intent(this, ViolationPayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(JxGlobal.putExtra.violation_pay_bean_extra, bean);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.push_bottom_in, 0);
    }

    /**
     * 去绑卡页面
     */
    private void byCardHome() {
        MobclickAgent.onEvent(Utils.getContext(), Config.getUMengID(11));
        DialogUtils.remindDialog(this,
                "温馨提示", "您还未绑卡，暂时无法进行缴费", "取消", "立即绑卡",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act.getInstance().gotoIntentLogin(ViolationListActivity.this, UnblockedCardActivity.class);
                    }
                });
    }

    /**
     * 罚单号
     */
    public void showDialogToCodequery(final ViolationBean bean) {
        DialogUtils.remindDialog(this, "温馨提示", "请使用处罚决定书编号进行缴纳", "取消", "罚单编号",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act.getInstance().gotoIntent(ViolationListActivity.this, Codequery.class);
                    }
                });
    }

    private void initViewPager() {
        String[] title = new String[]{"全部", "未处理", "已处理"};
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

        mViewPager.setCurrentItem(1);
    }

    @Override
    protected void DestroyViewAndThing() {
        orderUnStatusFragment = null;
        orderPayStatusFragment = null;
        orderAllStatusFragment = null;

        if (mPresenter != null) mPresenter.unSubscribe();
        if (mPayCarList != null) mPayCarList.clear();
        if (mFragmentList != null) mFragmentList.clear();
    }

    @Override
    public void setPresenter(IViolationListFtyContract.IViolationListFtyPresenter presenter) {
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
     * 查询列表数据失败
     */
    @Override
    public void searchViolationError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);

        if (orderUnStatusFragment != null) orderUnStatusFragment.setPayOrderListData(null);
        if (orderPayStatusFragment != null) orderPayStatusFragment.setPayOrderListData(null);
        if (orderAllStatusFragment != null) orderAllStatusFragment.setPayOrderListData(null);
    }

    /**
     * 分发数据失败
     */
    @Override
    public void dataDistributionError(String message, int value) {
        ToastUtils.toastShort(message);
        if (value == 0) {
            if (orderUnStatusFragment != null)
                orderUnStatusFragment.setPayOrderListData(null);
        } else {
            if (orderPayStatusFragment != null)
                orderPayStatusFragment.setPayOrderListData(null);
        }
    }

    /**
     * 未处理
     */
    @Override
    public void nonPaymentData(List<ViolationBean> beanList) {
        if (orderUnStatusFragment != null)
            orderUnStatusFragment.setPayOrderListData(beanList);
    }

    /**
     * 已处理
     */
    @Override
    public void havePaymentData(List<ViolationBean> beanList) {
        if (orderPayStatusFragment != null)
            orderPayStatusFragment.setPayOrderListData(beanList);
    }

    /**
     * 全部
     */
    @Override
    public void allPaymentData(ViolationResult rspInfo) {
        List<ViolationBean> beanList = rspInfo.getViolationInfo();
        if (orderAllStatusFragment != null) {
            orderAllStatusFragment.setPayOrderListData(beanList);
        }
    }

    /**
     * 获取列表请求参数
     */
    @Override
    public ViolationDTO getViolationDTO() {
        if (mViolationDTO == null) return null;

        mViolationDTO.setProcessste("2");
        mViolationDTO.setToken(RSAUtils.strByEncryption(MainRouter.getPhoneDeviceId(), true));

        return mViolationDTO;
    }

    /**
     * 传值后台
     */
    @Override
    public ViolationCarDTO getViolationCarDTO() {
        ViolationCarDTO violationCarDTO = new ViolationCarDTO();
        String carnum = (String) LoginData.getInstance().mHashMap.get("carnum");
        String enginenum = (String) LoginData.getInstance().mHashMap.get("enginenum");

        violationCarDTO.setCarnum(carnum);
        violationCarDTO.setCarnumtype(mViolationDTO.getCarnumtype());
        violationCarDTO.setEnginenum(enginenum);

        return violationCarDTO;
    }

    /**
     * 获取可缴费车辆
     */
    @Override
    public void getPayCarsError(String message) {
        mPayCarOk = false;
        ToastUtils.toastShort(message);
    }

    @Override
    public void getPayCarsSucceed(PayCarBean payCarBean) {
        mPayCarOk = true;
        mPayCarList = payCarBean.getUserCarsInfo();
    }

    /**
     * 页面回调
     * 1、驾校报名成功页面
     * 2、订单详情页面
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //        if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
        //                && resultCode == JxGlobal.resultCode.web_order_id_succeed) {
        //
        //            Intent intent = new Intent();
        //            intent.putExtra(JxGlobal.putExtra.fahrschule_position_extra, 2);
        //            Act.getInstance().gotoLoginByIntent(this, FahrschuleActivity.class, intent);
        //
        //        } else if (requestCode == JxGlobal.requestCode.fahrschule_order_num_web
        //                && resultCode == JxGlobal.resultCode.web_order_id_error && data != null) {
        //            //前往 订单详情页面
        //            String orderId = data.getStringExtra(JxGlobal.putExtra.web_order_id_extra);
        //            Intent intent = new Intent(this, OrderDetailActivity.class);
        //            intent.putExtra(JxGlobal.putExtra.web_order_id_extra, orderId);
        //            startActivity(intent);
        //        }
    }

    /**
     * 关闭fragment
     */
    public void closeFragment() {
        closeFragment(0);
    }

    public interface RefreshListener {

        void refreshListData(int position);

        void doClickPay(ViolationBean bean);
    }
}
