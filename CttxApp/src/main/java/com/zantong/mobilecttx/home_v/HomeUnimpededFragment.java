package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.contract.IUnimpededFtyContract;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
import com.zantong.mobilecttx.eventbus.GetMsgAgainEvent;
import com.zantong.mobilecttx.home.adapter.HorizontalCarViolationAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.adapter.MainBannerImgHolderView;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.home.bean.HomeBean;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeNotice;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerBean;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.presenter.home.UnimpededFtyPresenter;
import com.zantong.mobilecttx.push_v.PushBean;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.MessageCountBean;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.widght.MainScrollUpAdvertisementView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.qqtheme.framework.custom.banner.CBViewHolderCreator;
import cn.qqtheme.framework.custom.banner.ConvenientBanner;
import cn.qqtheme.framework.global.JxConfig;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 畅通主页面
 */
public class HomeUnimpededFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, IUnimpededFtyContract.IUnimpededFtyView {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Hello blank fragment
     */
    private ConvenientBanner mCustomConvenientBanner;
    private ImageView mImgMsg;
    private TextView mTvMsgCount;
    /**
     * 畅通车友会
     */
    private TextView mTvTitle;
    private ImageView mImgScan;

    /**
     * 扫罚单
     */
    private TextView mTvScan;

    private ImageView mImgTrumpet;
    private ImageView mImgLabel;

    private MainScrollUpAdvertisementView mCustomGrapevine;
    private HorizontalInfiniteCycleViewPager mCustomViolation;
    /**
     * P 指示器
     */
    private IUnimpededFtyContract.IUnimpededFtyPresenter mPresenter;
    /**
     * 小喇叭数据
     */
    private List<HomeNotice> mHomeNotices = Collections.synchronizedList(new ArrayList<HomeNotice>());

    /**
     * 违章车adapter
     */
    private HorizontalCarViolationAdapter mCarViolationAdapter;
    private HomeMainActivity.MessageListener mHomeMainListener;
    private List<UserCarInfoBean> mUserCarInfoBeanList = new ArrayList<>();
    private TextView mTvAppraisement;

    private ViewPager mViewPager;
    private LinearLayout mTabLayout;
    private List<Fragment> mPagerList;
    private HomePagerFragment_0 pagerFragment_0;
    private HomePagerFragment_1 pagerFragment_1;

    /**
     * 会多次刷新数据 ^3^
     */
    @Override
    public void onResume() {
        super.onResume();

        resumeDataVisible();

        startCampaignCustom(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        startCampaignCustom(true);
    }

    public static HomeUnimpededFragment newInstance() {
        return new HomeUnimpededFragment();
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_home_unimpeded;
    }

    /**
     * 下拉数据设置
     */
    @Override
    protected void onRefreshData() {
        resumeDataVisible();
    }

    @Override
    protected void initFragmentView(View view) {
        EventBus.getDefault().register(this);

        initView(view);

        UnimpededFtyPresenter mPresenter = new UnimpededFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(IUnimpededFtyContract.IUnimpededFtyPresenter presenter) {
        mPresenter = presenter;
    }

    private void initView(View view) {
        mCustomConvenientBanner = (ConvenientBanner) view.findViewById(R.id.custom_convenientBanner);

        mTvMsgCount = (TextView) view.findViewById(R.id.tv_msg_count);
        mTvMsgCount.setOnClickListener(this);
        mImgMsg = (ImageView) view.findViewById(R.id.img_msg);
        mImgMsg.setOnClickListener(this);

        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgScan = (ImageView) view.findViewById(R.id.img_scan);
        mImgScan.setOnClickListener(this);
        mTvScan = (TextView) view.findViewById(R.id.tv_scan);
        mTvScan.setOnClickListener(this);

        mImgTrumpet = (ImageView) view.findViewById(R.id.img_trumpet);
        mImgLabel = (ImageView) view.findViewById(R.id.img_label);
        mCustomGrapevine = (MainScrollUpAdvertisementView) view.findViewById(R.id.custom_grapevine);
        mCustomViolation = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.custom_violation);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTabLayout = (LinearLayout) view.findViewById(R.id.tabLayout);
        if (mPagerList == null) mPagerList = new ArrayList<>();
        initPagerFragment();
        initViewPager();
    }

    private void initPagerFragment() {
        if (mPagerList != null && !mPagerList.isEmpty()) mPagerList.clear();

        pagerFragment_0 = HomePagerFragment_0.newInstance();
        mPagerList.add(pagerFragment_0);
        pagerFragment_1 = HomePagerFragment_1.newInstance();
        mPagerList.add(pagerFragment_1);
    }

    private void initViewPager() {
        OrderFragmentAdapter mainFragmentAdapter =
                new OrderFragmentAdapter(getChildFragmentManager(), mPagerList, null);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mPagerList.size() - 1);//设置预加载
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotViewList.size(); i++) {
                    dotViewList.get(i).setBackgroundResource(
                            i == position ? R.mipmap.icon_dot_sel : R.mipmap.icon_dot_nor);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        //        mTabLayout.setupWithViewPager(mViewPager);

        initTabLayDots(mPagerList.size());
    }

    // 放圆点的View的list
    private List<View> dotViewList = new ArrayList<>();

    private void initTabLayDots(int len) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(16, 16);
        layoutParams.setMargins(12, 12, 12, 12);
        mTabLayout.removeAllViews();
        if (!dotViewList.isEmpty()) dotViewList.clear();
        for (int i = 0; i < len; i++) {
            ImageView dot = new ImageView(getContext());
            dot.setLayoutParams(layoutParams);
            if (i == 0) {
                dot.setBackgroundResource(R.mipmap.icon_dot_sel);
            } else {
                dot.setBackgroundResource(R.mipmap.icon_dot_nor);
            }
            dotViewList.add(dot);
            mTabLayout.addView(dot);
        }
    }

    @Override
    protected void onFirstDataVisible() {
        //广告页本地加载
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.mipmap.banner);
        mCustomConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        //小喇叭
        initScrollUp(mHomeNotices);
        //违章车辆
        mCarViolationAdapter = new HorizontalCarViolationAdapter(getContext(), mUserCarInfoBeanList);
        mCustomViolation.setAdapter(mCarViolationAdapter);

        if (mPresenter != null) mPresenter.getIndexLayer();
    }

    private void resumeDataVisible() {
        if (MainRouter.isUserLogin()) {
            mPresenter.getTextNoticeInfo();
        } else {
            getLocalCarInfo();
        }
        mPresenter.homePage();
    }

    private void initScrollUp(final List<HomeNotice> mDataLists) {
        if (mDataLists != null && mDataLists.size() == 0) {
            List<HomeNotice> mList = new ArrayList<>();
            mList.add(new HomeNotice("-1", 0, "暂无通知"));
            mCustomGrapevine.setData(mList);
        } else {
            mCustomGrapevine.setData(mDataLists);
        }
        mCustomGrapevine.setTextSize(12);
        mCustomGrapevine.setTimer(5000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        startCampaignCustom(hidden);
    }

    private void startCampaignCustom(boolean hidden) {
        if (hidden) {//不可见时
            if (mCustomGrapevine != null) mCustomGrapevine.stop();
            //停止翻页
            if (mCustomConvenientBanner != null) mCustomConvenientBanner.stopTurning();
        } else {
            if (mCustomGrapevine != null && !mCustomGrapevine.isRunning()) mCustomGrapevine.start();
            //开始自动翻页
            if (mCustomConvenientBanner != null && !mCustomConvenientBanner.isRunning())
                mCustomConvenientBanner.startTurning(4000);
        }
    }

    /**
     * 清除资源
     */
    @Override
    protected void DestroyViewAndThing() {
        mPresenter.unSubscribe();
        if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
        if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();

        EventBus.getDefault().removeStickyEvent(AddPushTrumpetEvent.class);
        EventBus.getDefault().removeStickyEvent(GetMsgAgainEvent.class);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadingProgress() {
        showDialogLoading();
    }

    @Override
    public void hideLoadingProgress() {
        hideDialogLoading();
    }

    /**
     * 操作首页数据
     */
    @Override
    public void homePageError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void homePageSucceed(HomeResponse result) {
        HomeBean bean = result.getData();
        //未读消息
        mTvMsgCount.setText(bean != null ? String.valueOf(bean.getMsgNum()) : "0");
        mTvMsgCount.setVisibility(bean != null ?
                bean.getMsgNum() == 0 ? View.INVISIBLE : View.VISIBLE
                : View.INVISIBLE);

        if (mHomeMainListener != null)
            mHomeMainListener.setTipOfNumber(2, bean != null ? bean.getMsgNum() : 0);
        //小喇叭通知
        if (bean != null && bean.getNotices() != null) {
            if (!bean.getNotices().isEmpty())
                mCustomGrapevine.setData(bean.getNotices());

            if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
            mHomeNotices.addAll(bean.getNotices());
        }

        //广告页面
        if (bean != null && bean.getAdvertisementResponse() != null) {
            List<HomeAdvertisement> advertisementResponse = bean.getAdvertisementResponse();
            mCustomConvenientBanner.setPages(
                    new CBViewHolderCreator<MainBannerImgHolderView>() {
                        @Override
                        public MainBannerImgHolderView createHolder() {
                            return new MainBannerImgHolderView();
                        }
                    },
                    advertisementResponse)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                    //设置翻页的效果，不需要翻页效果可用不设
                    .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
        }
    }

    /**
     * 操作违章车辆信息
     */
    @Override
    public void remoteCarInfoError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void getTextNoticeInfo(HomeCarResponse result) {
        if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();
        if (result != null && result.getData() != null) {
            List<UserCarInfoBean> infoBeanList = result.getData();
            //车辆数据保存处理
            LoginData.getInstance().payData.clear();
            LoginData.getInstance().mCarNum = infoBeanList.size();
            LoginData.getInstance().mServerCars = decodeCarInfo(infoBeanList);

            mUserCarInfoBeanList.addAll(infoBeanList);
        }
        //违章车辆
        mCarViolationAdapter.notifyDataSetChanged(mUserCarInfoBeanList);
        mCustomViolation.notifyDataSetChanged();
    }

    /**
     * @deprecated 旧方法遗弃
     */
    @Override
    public void getRemoteCarInfoSucceed(UserCarsResult result) {
        if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();
        if (result != null && result.getRspInfo() != null) {
            List<UserCarInfoBean> infoBeanList = result.getRspInfo().getUserCarsInfo();

            LoginData.getInstance().payData.clear();
            LoginData.getInstance().mCarNum = infoBeanList.size();
            LoginData.getInstance().mServerCars = decodeCarInfo(infoBeanList);

            mUserCarInfoBeanList.addAll(infoBeanList);
        }
        //违章车辆
        mCarViolationAdapter.notifyDataSetChanged(mUserCarInfoBeanList);
        mCustomViolation.notifyDataSetChanged();
    }

    /**
     * 优惠活动
     */
    @Override
    public void indexLayerError(String message) {
        ToastUtils.toastShort(message);
    }

    public TextView mCountTv;
    public TextView mmCloseTv;

    @Override
    public void indexLayerSucceed(IndexLayerResponse result) {
        IndexLayerBean bean = result.getData();
        if (bean == null) return;

        DialogUtils.createActionDialog(getActivity(), 3, bean.getImgUrl(), bean.getPageUrl(),
                new DialogUtils.ActionADOnClick() {
                    @Override
                    public void textCount(TextView mCount, TextView mClose) {
                        mCountTv = mCount;
                        mmCloseTv = mClose;
                    }
                });

        if (mPresenter != null) mPresenter.startCountDown();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void countDownTextView(long count) {
        if (mCountTv != null) mCountTv.setText(count + "s");
    }

    @Override
    public void countDownCompleted() {
        if (mmCloseTv != null)
            mmCloseTv.setVisibility(View.VISIBLE);
        if (mCountTv != null)
            mCountTv.setVisibility(View.GONE);
    }

    /**
     * 车辆数据解密
     */
    private List<UserCarInfoBean> decodeCarInfo(List<UserCarInfoBean> carInfoBeanList) {
        List<UserCarInfoBean> arrayList = new ArrayList<>();
        for (UserCarInfoBean userCarInfoBean : carInfoBeanList) {
            userCarInfoBean.setEnginenum(Des3.decode(userCarInfoBean.getEnginenum()));
            userCarInfoBean.setCarnum(Des3.decode(userCarInfoBean.getCarnum()));

            if (!TextUtils.isEmpty(userCarInfoBean.getIspaycar())
                    && userCarInfoBean.getIspaycar().equals("1"))

                LoginData.getInstance().payData.add(userCarInfoBean);
            arrayList.add(userCarInfoBean);
        }
        return arrayList;
    }

    /**
     * 本地车辆数据操作
     */
    private void getLocalCarInfo() {
        List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
        if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();
        if (list != null && list.size() > 0)
            for (CarInfoDTO carInfoDTO : list) {
                UserCarInfoBean userCarInfoBean = new UserCarInfoBean();
                userCarInfoBean.setCarnum(carInfoDTO.getCarnum());
                userCarInfoBean.setCarnumtype(carInfoDTO.getCarnumtype());
                userCarInfoBean.setEnginenum(carInfoDTO.getEnginenum());
                mUserCarInfoBeanList.add(userCarInfoBean);
            }

        mCarViolationAdapter.notifyDataSetChanged(mUserCarInfoBeanList);
        mCustomViolation.notifyDataSetChanged();

        LoginData.getInstance().mLocalCars = list;
        LoginData.getInstance().mCarNum = list != null ? list.size() : 0;
    }

    /**
     * 小喇叭更新通知
     */
    public synchronized void updateNoticeMessage(PushBean bean) {
        List<HomeNotice> notices = new ArrayList<>();
        if (mHomeNotices.isEmpty() || mHomeNotices == null) {
            notices.add(new HomeNotice(bean.getId(), 3, bean.getContent(), bean.isNewMeg()));
        } else {
            String newId = bean.getId();
            for (HomeNotice notice : mHomeNotices) {
                if (notice.getId().equals(newId)) {
                    notice.setNewMeg(bean.isNewMeg());
                } else {
                    notices.add(new HomeNotice(bean.getId(), 3, bean.getContent(), bean.isNewMeg()));
                    break;
                }
            }
            notices.addAll(mHomeNotices);
        }
        mCustomGrapevine.setData(notices);

        if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
        mHomeNotices.addAll(notices);
    }

    /**
     * 标记小喇叭
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataSynEvent(AddPushTrumpetEvent event) {
        if (event == null) return;
        PushBean bean = event.getPushBean();
        updateNoticeMessage(bean);
    }

    /**
     * 再次获取消息数量
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataSynEvent(GetMsgAgainEvent event) {
        if (event != null && event.getStatus()) {
            BaseDTO dto = new BaseDTO();
            dto.setUsrId(RSAUtils.strByEncryption(LoginData.getInstance().userID, true));
            CarApiClient.getUnReadMsgCount(this.getActivity(), dto, new CallBack<MessageCountResponse>() {
                @Override
                public void onSuccess(MessageCountResponse result) {
                    if (result.getResponseCode() == 2000) {
                        MessageCountBean bean = result.getData();
                        //未读消息
                        mTvMsgCount.setText(bean != null ? String.valueOf(bean.getCount()) : "0");
                        mTvMsgCount.setVisibility(bean != null ?
                                bean.getCount() == 0 ? View.INVISIBLE : View.VISIBLE : View.INVISIBLE);

                        if (mHomeMainListener != null)
                            mHomeMainListener.setTipOfNumber(2, bean != null ? bean.getCount() : 0);
                    }
                }
            });
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_msg://消息
            case R.id.tv_msg_count:
                JxConfig.getInstance().eventIdByUMeng(15);
                MainRouter.gotoMegTypeActivity(getActivity());
                break;
            case R.id.img_scan://扫描
            case R.id.tv_scan:
                JxConfig.getInstance().eventIdByUMeng(17);
                takeCapture();
                break;
            default:
                break;
        }
    }

    /**
     * 违章单扫描
     */
    public void takeCapture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}
            );
        } else {
            gotoCapture();
        }
    }

    private void gotoCapture() {MainRouter.gotoCaptureActivity(getActivity());}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        gotoCapture();
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("相机权限被拒绝，请手机设置中打开");
    }

    public void setMessageListener(HomeMainActivity.MessageListener messageListener) {
        mHomeMainListener = messageListener;
    }
}
