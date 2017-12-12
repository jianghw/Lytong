package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.bean.BaseResponse;
import com.tzly.ctcyh.router.custom.banner.CBViewHolderCreator;
import com.tzly.ctcyh.router.custom.banner.ConvenientBanner;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.contract.IUnimpededFtyContract;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
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
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.presenter.home.UnimpededFtyPresenter;
import com.zantong.mobilecttx.push_v.PushBean;
import com.zantong.mobilecttx.push_v.PushTipService;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.violation_v.LicenseCheckGradeActivity;
import com.zantong.mobilecttx.violation_v.LicenseDetailActivity;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.widght.MainScrollUpAdvertisementView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tzly.ctcyh.router.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 畅通主页面
 */
public class HomeUnimpededFragment extends RefreshFragment
        implements View.OnClickListener, IUnimpededFtyContract.IUnimpededFtyView {

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
    private List<HomeNotice> mHomeNotices =
            Collections.synchronizedList(new ArrayList<HomeNotice>());
    /**
     * 违章车adapter
     */
    private HorizontalCarViolationAdapter mCarViolationAdapter;

    private List<UserCarInfoBean> mUserCarInfoBeanList = new ArrayList<>();

    //    private ViewPager mViewPager;
    //    private LinearLayout mTabLayout;
    private List<Fragment> mPagerList;
    private HomePagerFragment_0 pagerFragment_0;
    private HomePagerFragment_1 pagerFragment_1;

    private TextView mTvLicense;
    private TextView mTvAppraisement;
    private TextView mTvCheck;
    private TextView mTvDrive;
    private TextView mTvOil;
    private TextView mTvMap;
    private TextView mTvVehicle;
    private TextView mTvRoadside;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 会多次刷新数据 ^3^
     */
    @Override
    public void onResume() {
        super.onResume();
        startCampaignCustom(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        startCampaignCustom(true);
    }

    /**
     * 清除资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
        if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
        if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();

        EventBus.getDefault().removeStickyEvent(AddPushTrumpetEvent.class);
        EventBus.getDefault().unregister(this);
    }

    public static HomeUnimpededFragment newInstance() {
        return new HomeUnimpededFragment();
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int fragmentView() {
        return R.layout.fragment_home_unimpeded;
    }

    @Override
    protected void bindFragment(View fragment) {
        EventBus.getDefault().register(this);

        initView(fragment);

        UnimpededFtyPresenter mPresenter = new UnimpededFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);

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
        mTvScan = (TextView) view.findViewById(R.id.tv_scan);
        mTvScan.setOnClickListener(this);

        mImgTrumpet = (ImageView) view.findViewById(R.id.img_trumpet);
        mImgLabel = (ImageView) view.findViewById(R.id.img_label);
        mCustomGrapevine = (MainScrollUpAdvertisementView) view.findViewById(R.id.custom_grapevine);
        mCustomViolation = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.custom_violation);

        mTvLicense = (TextView) view.findViewById(R.id.tv_license);
        mTvLicense.setOnClickListener(this);
        mTvAppraisement = (TextView) view.findViewById(R.id.tv_appraisement);
        mTvAppraisement.setOnClickListener(this);
        mTvCheck = (TextView) view.findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(this);
        mTvDrive = (TextView) view.findViewById(R.id.tv_drive);
        mTvDrive.setOnClickListener(this);
        mTvOil = (TextView) view.findViewById(R.id.tv_oil);
        mTvOil.setOnClickListener(this);
        mTvMap = (TextView) view.findViewById(R.id.tv_map);
        mTvMap.setOnClickListener(this);
        mTvVehicle = (TextView) view.findViewById(R.id.tv_vehicle);
        mTvVehicle.setOnClickListener(this);
        mTvRoadside = (TextView) view.findViewById(R.id.tv_roadside);
        mTvRoadside.setOnClickListener(this);

        //        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //        mTabLayout = (LinearLayout) view.findViewById(R.id.tabLayout);

        //        if (mPagerList == null) mPagerList = new ArrayList<>();
        //        initPagerFragment();
        //        initViewPager();

//        final RelativeLayout mTv = (RelativeLayout) view.findViewById(R.id.ry_banner);
//        LogUtils.e("===>" + mTv.getWidth() + "/" + mTv.getHeight());
//        ViewTreeObserver vto = mTv.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                int height = mTv.getMeasuredHeight();
//                int width = mTv.getMeasuredWidth();
//                LogUtils.e("===>" + width + "/" + height);
//                return true;
//            }
//        });
    }

    private void initPagerFragment() {
        if (mPagerList != null && !mPagerList.isEmpty()) mPagerList.clear();

        pagerFragment_0 = HomePagerFragment_0.newInstance();
        mPagerList.add(pagerFragment_0);
        pagerFragment_1 = HomePagerFragment_1.newInstance();
        mPagerList.add(pagerFragment_1);
    }

   /* private void initViewPager() {
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
        mTabLayout.setupWithViewPager(mViewPager);
        initTabLayDots(mPagerList.size());
    }*/

    // 放圆点的View的list
    private List<View> dotViewList = new ArrayList<>();

    private void initTabLayDots(int len) {
        //        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(16, 16);
        //        layoutParams.setMargins(12, 12, 12, 12);
        //        mTabLayout.removeAllViews();
        //        if (!dotViewList.isEmpty()) dotViewList.clear();
        //        for (int i = 0; i < len; i++) {
        //            ImageView dot = new ImageView(getContext());
        //            dot.setLayoutParams(layoutParams);
        //            if (i == 0) {
        //                dot.setBackgroundResource(R.mipmap.icon_dot_sel);
        //            } else {
        //                dot.setBackgroundResource(R.mipmap.icon_dot_nor);
        //            }
        //            dotViewList.add(dot);
        //            mTabLayout.addView(dot);
        //        }
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

    /**
     * 初次加载页面是不调用
     */
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
     * 数据初始化
     */
    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getIndexLayer();

        if (MainRouter.isUserLogin())
            mPresenter.getTextNoticeInfo();
        else
            getLocalCarInfo();
        if (mPresenter != null) mPresenter.homePage();
    }

    /**
     * 啊不用此方法 不能出现未加载状态页面
     */
    @Override
    protected void responseData(Object response) {}

    /**
     * 操作首页数据
     */
    @Override
    public void homePageError(String message) {
        toastShort(message);
    }

    /**
     * 未读消息
     */
    public void unMessageCount(int position, int number) {
        //未读消息
        if (mTvMsgCount != null) {
            mTvMsgCount.setText(String.valueOf(number));
            mTvMsgCount.setVisibility(number <= 0 ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @Override
    public void homePageSucceed(HomeResponse result) {
        //启动服务标记小圆点
        Intent i = new Intent(getActivity(), PushTipService.class);
        i.setAction("com.custom.service.push.tip");
        getActivity().startService(i);

        HomeBean bean = result.getData();
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
        String imgUrl = bean.getImgUrl();
        String pageUrl = bean.getPageUrl();
        if (TextUtils.isEmpty(imgUrl) && !TextUtils.isEmpty(pageUrl)) {
            MainRouter.gotoHtmlActivity(getActivity(), "优惠活动", pageUrl);
        } else {
            DialogUtils.createActionDialog(getActivity(), 3, imgUrl, pageUrl,
                    new DialogUtils.ActionADOnClick() {
                        @Override
                        public void textCount(TextView mCount, TextView mClose) {
                            mCountTv = mCount;
                            mmCloseTv = mClose;
                        }
                    });
        }
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
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        int goodId = 0;
        switch (v.getId()) {
            case R.id.img_msg://消息
            case R.id.tv_msg_count:
                MobUtils.getInstance().eventIdByUMeng(15);
                MainRouter.gotoMegTypeActivity(getActivity());
                break;//扫描
            case R.id.tv_scan://扫描
                MobUtils.getInstance().eventIdByUMeng(17);
                takeCapture();
                break;
            case R.id.tv_drive://国际驾照
                MobUtils.getInstance().eventIdByUMeng(35);
                goodId = 1;
                InternationalDrivingDocument();
                break;
            case R.id.tv_license://驾驶证查分
                MobUtils.getInstance().eventIdByUMeng(7);
                goodId = 2;
                licenseCheckGrade();
                break;
            case R.id.tv_appraisement://爱车估值
                MobUtils.getInstance().eventIdByUMeng(34);
                goodId = 3;
                carValuation();
                break;
            case R.id.tv_check://年检
                MobUtils.getInstance().eventIdByUMeng(4);
                goodId = 4;
                gotoCheckHtml();
                break;
            case R.id.tv_oil://优惠加油
                goodId = 5;
                showContacts();
                break;
            case R.id.tv_map:
                goodId = 6;
                enterDrivingActivity();
                break;
            case R.id.tv_vehicle://车管所
                goodId = 7;
                enterVehicleActivity();
                break;
            case R.id.tv_roadside://车道
                goodId = 8;
                enterRoadsideActivity();
                break;
            default:
                break;
        }
        if (goodId < 1) return;
        CarApiClient.commitAdClick(Utils.getContext(), goodId, "3",
                new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                    }
                });
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

    private void gotoCheckHtml() {
        MainRouter.gotoHtmlActivity(getActivity(), "年检服务",
                BuildConfig.App_Url ? "http://139.196.183.121:3000/myCar"
                        : "http://nianjian.liyingtong.com/myCar");
    }

    protected void licenseCheckGrade() {
        LicenseFileNumDTO bean = SPUtils.getInstance().getLicenseFileNumDTO();
        if (!TextUtils.isEmpty(MainRouter.getUserFilenum())
                && !TextUtils.isEmpty(MainRouter.getUserGetdate()) || bean != null) {
            LicenseFileNumDTO loginBean = new LicenseFileNumDTO();
            loginBean.setFilenum(MainRouter.getUserFilenum());
            loginBean.setStrtdt(MainRouter.getUserGetdate());

            Intent intent = new Intent(getActivity(), LicenseDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(LicenseCheckGradeActivity.KEY_BUNDLE, bean != null ? bean : loginBean);
            bundle.putBoolean(LicenseCheckGradeActivity.KEY_BUNDLE_FINISH, true);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Act.getInstance().gotoIntentLogin(getActivity(), LicenseCheckGradeActivity.class);
        }
    }

    protected void carValuation() {
        MainRouter.gotoHtmlActivity(getActivity(),
                "爱车估值", "http://m.jingzhengu.com/xiansuo/sellcar-changtongcheyouhui.html");
    }

    protected void InternationalDrivingDocument() {
        MainRouter.gotoHtmlActivity(getActivity(),
                "国际驾照", "https://m.huizuche.com/Cdl/Intro3/ctcyh");
    }

    /**
     * 车管所指导
     */
    private void enterVehicleActivity() {
        MainRouter.gotoHtmlActivity(getActivity(),
                "车管所指导", "http://sh.122.gov.cn/shjjappapi/cgs2.html");
    }

    private void enterRoadsideActivity() {
        MainRouter.gotoHtmlActivity(getActivity(),
                "道路救援", "https://www.ipaosos.com/wshop/createorder/");
    }

    /**
     * 加油地图
     */
    public void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 3000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoOilMap();
        }
    }

    private void gotoOilMap() {
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.map_type_extra, JxGlobal.MapType.annual_oil_map);
        Act.getInstance().gotoLoginByIntent(getActivity(), BaiduMapParentActivity.class, intent);
    }

    /**
     * 进入地图年检页面
     */
    public void enterDrivingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoMap();
        }
    }

    private void gotoMap() {
        MainRouter.gotoMapActivity(getActivity());
    }

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

    @PermissionSuccess(requestCode = 3000)
    public void doMapPermissionSuccess() {
        gotoOilMap();
    }

    @PermissionFail(requestCode = 3000)
    public void doMapPermissionFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        gotoMap();
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }

}
