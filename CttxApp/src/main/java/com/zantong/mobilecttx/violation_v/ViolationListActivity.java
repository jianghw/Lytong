package com.zantong.mobilecttx.violation_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.home.activity.Codequery;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.violation_p.IViolationListContract;
import com.zantong.mobilecttx.violation_p.ViolationListPresenter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.ArrayList;
import java.util.List;

import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;

/**
 * 违法信息列表页面
 */
public class ViolationListActivity extends AbstractBaseActivity
        implements IViolationListContract.IViolationListView, IViolationListUi {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    private ViolationListFragment orderAllStatusFragment = null;
    private ViolationListFragment orderUnStatusFragment = null;
    private ViolationListFragment orderPayStatusFragment = null;

    private IViolationListContract.IViolationListPresenter mPresenter;
    /**
     * 传值
     */
    private ViolationDTO mViolationDTO;
    private String mTitle;


    @Override
    protected int initContentView() {
        return R.layout.activity_violation_list;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mViolationDTO = (ViolationDTO) bundle.getSerializable("params");
            }
        }
        if (mViolationDTO != null) mTitle = mViolationDTO.getCarnum();
    }

    @Override
    protected void bindFragment() {
        titleContent(mTitle);

        initView();
        if (mFragmentList == null) mFragmentList = new ArrayList<>();
        initFragment();
        initViewPager();

        ViolationListPresenter presenter = new ViolationListPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    public void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initFragment() {
        if (mFragmentList != null && !mFragmentList.isEmpty()) mFragmentList.clear();

        orderAllStatusFragment = ViolationListFragment.newInstance();
        mFragmentList.add(orderAllStatusFragment);

        orderUnStatusFragment = ViolationListFragment.newInstance();
        mFragmentList.add(orderUnStatusFragment);

        orderPayStatusFragment = ViolationListFragment.newInstance();
        mFragmentList.add(orderPayStatusFragment);
    }

    private void initViewPager() {
        String[] title = new String[]{"全部", "未处理", "已处理"};
        OrderFragmentAdapter mainFragmentAdapter =
                new OrderFragmentAdapter(getSupportFragmentManager(), mFragmentList, title);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
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
    protected void initContentData() {
        if (mPresenter != null) mPresenter.searchViolation();
    }

    /**
     * 刷新
     */
    @Override
    public void refreshListData(int position) {
        initContentData();
    }

    /**
     * 支付
     */
    @Override
    public void doClickPay(ViolationBean bean) {
        int processste = bean.getProcessste();//处理状态

        if (!MainRouter.isUserLogin()) {
            MainRouter.gotoLoginActivity(this);
        } else if (processste == 2 || processste == 3) {
            showDialogToCodequery(bean);
        } else {
            showPayFragment(bean);
        }
    }

    /**
     * 付款fragment
     */
    private void goToPayFragment(ViolationBean bean, String violationnum) {
        String penaltyNum = violationnum.substring(6, 7);
        if ("1".equals(penaltyNum) || "2".equals(penaltyNum)) {//是否处罚决定书
            showPayFragment(bean);
        } else {
            toastShort("是否处罚决定书判别出错!");
        }
    }

    /**
     * 支付弹出框
     * ViolationBean 不含发动机号码及车牌号加过密
     */
    private void showPayFragment(ViolationBean bean) {
        Intent intent = new Intent(this, ViolationPayActivity.class);
        Bundle bundle = new Bundle();

        bean.setCarnum(Des3.decode(bean.getCarnum()));
        bean.setEnginenum(mViolationDTO.getEnginenum());
        bundle.putParcelable(JxGlobal.putExtra.violation_pay_bean_extra, bean);
        intent.putExtras(bundle);
        startActivity(intent);

        overridePendingTransition(R.anim.set_translate_in, 0);
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
                        gotoCodequery();
                    }
                });
    }

    private void gotoCodequery() {
        Act.getInstance().gotoIntent(this, Codequery.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 去规则页面
     */
    @Override
    protected void onDestroy() {
        if (mViolationDTO != null)
            MainRouter.gotoActiveActivity(this, 1, mViolationDTO.getRegisterDate());
        super.onDestroy();

        if (mPresenter != null) mPresenter.unSubscribe();
        orderUnStatusFragment = null;
        orderPayStatusFragment = null;
        orderAllStatusFragment = null;
        if (mFragmentList != null) mFragmentList.clear();
    }

    @Override
    public void setPresenter(IViolationListContract.IViolationListPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 查询列表数据失败
     */
    @Override
    public void searchViolationError(String message) {
        toastShort(message);

        if (orderUnStatusFragment != null) orderUnStatusFragment.responseError(message);
        if (orderPayStatusFragment != null) orderPayStatusFragment.responseError(message);
        if (orderAllStatusFragment != null) orderAllStatusFragment.responseError(message);
    }

    /**
     * 分发数据失败
     */
    @Override
    public void dataDistributionError(String message, int value) {
        toastShort(message);
        if (value == 0) {
            if (orderUnStatusFragment != null)
                orderUnStatusFragment.responseError(message);
        } else {
            if (orderPayStatusFragment != null)
                orderPayStatusFragment.responseError(message);
        }
    }

    /**
     * 全部
     */
    @Override
    public void allPaymentData(ViolationResult rspInfo) {
        List<ViolationBean> beanList = rspInfo.getViolationInfo();
        if (orderAllStatusFragment != null) {
            orderAllStatusFragment.responseSucceed(beanList);
        }
    }

    /**
     * 未处理
     */
    @Override
    public void nonPaymentData(List<ViolationBean> beanList) {
        if (orderUnStatusFragment != null)
            orderUnStatusFragment.responseSucceed(beanList);
    }

    /**
     * 已处理
     */
    @Override
    public void havePaymentData(List<ViolationBean> beanList) {
        if (orderPayStatusFragment != null)
            orderPayStatusFragment.responseSucceed(beanList);
    }

    /**
     * 获取列表请求参数
     */
    @Override
    public ViolationDTO getViolationDTO() {
        return mViolationDTO;
    }

    /**
     * 传值后台
     */
    @Override
    public ViolationCarDTO getViolationCarDTO() {
        ViolationCarDTO violationCarDTO = new ViolationCarDTO();

        violationCarDTO.setCarnum(mViolationDTO.getCarnum());
        violationCarDTO.setCarnumtype(mViolationDTO.getCarnumtype());
        violationCarDTO.setEnginenum(mViolationDTO.getEnginenum());
        return violationCarDTO;
    }

}
