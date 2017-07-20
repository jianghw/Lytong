package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.card.activity.CardHomeActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.home.activity.Codequery;
import com.zantong.mobilecttx.interf.IViolationListFtyContract;
import com.zantong.mobilecttx.order.activity.OrderDetailActivity;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.presenter.weizhang.ViolationListPresenter;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;
import com.zantong.mobilecttx.weizhang.fragment.ViolationListFragment;
import com.zantong.mobilecttx.weizhang.fragment.ViolationPayFragment;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * 违章信息列表页面
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
    /**
     * 已绑定车辆
     */
    private List<PayCar> mPayCarList;
    /**
     * 是否获取已绑车辆 标记
     */
    private boolean mPayCarOk;
    /**
     * 支付类型
     */
    private int mPayType = 1;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("plateNum");
            initTitleContent(title);

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

        orderUnStatusFragment = ViolationListFragment.newInstance();
        orderUnStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderUnStatusFragment);

        orderPayStatusFragment = ViolationListFragment.newInstance();
        orderPayStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderPayStatusFragment);

        orderAllStatusFragment = ViolationListFragment.newInstance();
        orderAllStatusFragment.setRefreshListener(getRefreshListener());
        mFragmentList.add(orderAllStatusFragment);
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
        int processste = bean.getProcessste();

        if (!PublicData.getInstance().loginFlag) {
            Act.getInstance().gotoIntent(this, LoginActivity.class);
        } else if (processste == 2 || processste == 3) {
            showDialogToCodequery();
        } else {
            goToPayFragment(bean, violationnum);
        }
    }

    private void goToPayFragment(ViolationBean bean, String violationnum) {
        if ("1".equals(violationnum) || "2".equals(violationnum)) {//是否处罚决定书
            showPayFragment(bean);
        } else {
            if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {//未綁卡
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
                            break;
                        }
                    }
                    ToastUtils.toastShort("当前车辆为非绑定车辆，可去车辆管理进行改绑");
                }
            }
        }
    }

    private void showPayFragment(ViolationBean bean) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViolationPayFragment payFragment = ViolationPayFragment.newInstance(bean);
        FragmentUtils.replaceFragment(
                fragmentManager, payFragment, R.id.lay_content, true);
    }

    /**
     * 去绑卡页面
     */
    private void byCardHome() {
        MobclickAgent.onEvent(ContextUtils.getContext(), Config.getUMengID(11));
        DialogUtils.remindDialog(this, "温馨提示", "您还未绑卡，暂时无法进行缴费", "取消", "立即绑卡",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act.getInstance().lauchIntentToLogin(ViolationListActivity.this, CardHomeActivity.class);
                    }
                });
    }

    /**
     * 罚单号
     */
    public void showDialogToCodequery() {
        DialogUtils.remindDialog(this, "温馨提示", "请使用处罚决定书编号进行缴纳", "取消", "罚单编号",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act.getInstance().lauchIntent(ViolationListActivity.this, Codequery.class);
                    }
                });
    }

    private void initViewPager() {
        String[] title = new String[]{"未处理", "已处理", "全部"};
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
        mViolationDTO.setProcessste("2");
        if (!TextUtils.isEmpty(PublicData.getInstance().userID)) {
            mViolationDTO.setToken(RSAUtils.strByEncryption(PublicData.getInstance().userID, true));
        } else if (!TextUtils.isEmpty(PublicData.getInstance().imei)) {
            mViolationDTO.setToken(RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
        } else {
            mViolationDTO.setToken(RSAUtils.strByEncryption(PushServiceFactory.getCloudPushService().getDeviceId(), true));
        }
        return mViolationDTO;
    }

    /**
     * 传值后台
     */
    @Override
    public ViolationCarDTO getViolationCarDTO() {
        ViolationCarDTO violationCarDTO = new ViolationCarDTO();
        String carnum = (String) PublicData.getInstance().mHashMap.get("carnum");
        String enginenum = (String) PublicData.getInstance().mHashMap.get("enginenum");

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
        if (requestCode == GlobalConstant.requestCode.fahrschule_order_num_web
                && resultCode == GlobalConstant.resultCode.web_order_id_succeed) {

            Intent intent = new Intent();
            intent.putExtra(GlobalConstant.putExtra.fahrschule_position_extra, 2);
            Act.getInstance().launchLoginByIntent(this, FahrschuleActivity.class, intent);

        } else if (requestCode == GlobalConstant.requestCode.fahrschule_order_num_web
                && resultCode == GlobalConstant.resultCode.web_order_id_error && data != null) {
            //前往 订单详情页面
            String orderId = data.getStringExtra(GlobalConstant.putExtra.web_order_id_extra);
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra(GlobalConstant.putExtra.web_order_id_extra, orderId);
            startActivity(intent);
        }
    }

    /**
     * 注意 支付类型
     */
    public int getPayType() {
        return mPayType;
    }

    public void setPayType(int payType) {
        mPayType = payType;
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