package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.google.gson.Gson;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.banner.CBViewHolderCreator;
import com.tzly.ctcyh.router.custom.banner.ConvenientBanner;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.router.custom.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.BaseAutoScrollUpTextView;
import com.zantong.mobilecttx.base.bean.UnimpededBannerBean;
import com.zantong.mobilecttx.base.bean.UnimpededBannerResponse;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.home_p.IUnimpededFtyContract;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
import com.zantong.mobilecttx.home_p.HorizontalCarViolationAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home_p.MainBannerImgHolderView;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.home.bean.HomeBean;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeNotice;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerBean;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.home_p.UnimpededFtyPresenter;
import com.zantong.mobilecttx.order.adapter.OrderFragmentAdapter;
import com.zantong.mobilecttx.push_v.PushBean;
import com.zantong.mobilecttx.push_v.PushTipService;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.CarBeautyActivity;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.widght.MainScrollUpAdvertisementView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tzly.ctcyh.router.custom.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 畅通主页面
 */
public class HomeUnimpededFragment extends RefreshFragment
        implements View.OnClickListener, IUnimpededFtyContract.IUnimpededFtyView, IDiscountsBanner {

    /**
     * Hello blank fragment
     */
    private ConvenientBanner mCustomConvenientBanner;
    private ImageView mImgMsg;
    private TextView mTvMsgCount;

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


    private ViewPager mViewPager;
    private LinearLayout mTabLayout;

    private List<Fragment> mPagerList;
    // 放圆点的View的list
    private List<View> dotViewList = new ArrayList<>();
    private OrderFragmentAdapter mainBannerAdapter;

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

        if (mPagerList != null) mPagerList.clear();
        if (!dotViewList.isEmpty()) dotViewList.clear();

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
        initViewPager();

        UnimpededFtyPresenter mPresenter = new UnimpededFtyPresenter(
                Injection.provideRepository(Utils.getContext()), this);

        //广告页本地加载
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.mipmap.default_330_160);
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
        mCarViolationAdapter = new HorizontalCarViolationAdapter(getActivity(), mUserCarInfoBeanList);
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
        mCustomGrapevine.setOnItemClickListener(new BaseAutoScrollUpTextView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!mHomeNotices.isEmpty()) {
                    HomeNotice listener = mHomeNotices.get(position);
                    if (TextUtils.isEmpty(listener.getDetail())) {
                        toastShort("暂时没有配置详情");
                    } else {
                        MainRouter.gotoRichTextActivity(getContext(), listener.getDetail());
                    }
                }
            }
        });
        mCustomViolation = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.custom_violation);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTabLayout = (LinearLayout) view.findViewById(R.id.tabLayout);
    }

    private void initScrollUp(List<HomeNotice> mDataLists) {
        if (mDataLists != null && mDataLists.size() == 0) {
            List<HomeNotice> mList = new ArrayList<>();
            mList.add(new HomeNotice("-1", 0, "暂无通知"));
            mCustomGrapevine.setData(mList);
        } else {
            mCustomGrapevine.setData(mDataLists);
        }
        mCustomGrapevine.setTextSize(11);
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
        if (mPresenter != null) mPresenter.getBanner();

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
    protected void responseData(Object response) {
    }

    /**
     * 数据出错
     */
    @Override
    public void dataError(String message) {
        toastShort(message);
    }

    /**
     * 坑位处理
     */
    @Override
    public void bannerSucceed(UnimpededBannerResponse result) {
        List<UnimpededBannerBean> lis = result.getData();
        if (lis != null && !lis.isEmpty()) {
            int pager = lis.size() % 10 != 0 ? lis.size() / 10 + 1 : lis.size() / 10;

            if (mPagerList == null) mPagerList = new ArrayList<>();
            if (!mPagerList.isEmpty()) mPagerList.clear();

            for (int i = 0; i < pager; i++) {
                //判断是否第一页和最后一页的特殊性
                HomePagerFragment_2 f = HomePagerFragment_2.newInstance(
                        lis.subList(i * 10, i == pager - 1 ? lis.size() : (i + 1) * 10));
                mPagerList.add(f);
            }
        }
        mainBannerAdapter.notifyDataSetChanged();
        initTabLayDots(mPagerList.size());
    }

    private void initViewPager() {
        if (mPagerList == null) mPagerList = new ArrayList<>();
        mainBannerAdapter = new OrderFragmentAdapter(getChildFragmentManager(), mPagerList, null);
        mViewPager.setAdapter(mainBannerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotViewList.size(); i++) {
                    dotViewList.get(i).setBackgroundResource(
                            i == position ? R.mipmap.icon_dot_sel : R.mipmap.icon_dot_nor);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTabLayDots(int len) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(16, 16);
        layoutParams.setMargins(6, 6, 12, 6);
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

        mTabLayout.setVisibility(len <= 1 ? View.GONE : View.VISIBLE);
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

        final HomeBean bean = result.getData();
        //小喇叭通知
        if (bean != null && bean.getNotices() != null) {
            if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
            mHomeNotices.addAll(bean.getNotices());

            if (!mHomeNotices.isEmpty()) {
                mCustomGrapevine.setData(mHomeNotices);
            }
        }

        //广告页面
        if (bean != null && bean.getAdvertisementResponse() != null) {
            List<HomeAdvertisement> advertisementResponse = bean.getAdvertisementResponse();
            mCustomConvenientBanner.setPages(
                    new CBViewHolderCreator<MainBannerImgHolderView>() {
                        @Override
                        public MainBannerImgHolderView createHolder() {
                            return new MainBannerImgHolderView(HomeUnimpededFragment.this);
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
     * 接口回调
     */
    @Override
    public void getStatistId(int statisticsId) {
        if (mPresenter != null) {
            mPresenter.saveStatisticsCount(String.valueOf(statisticsId));
        }
    }

    @Override
    public void gotoByPath(String url) {
        if (!TextUtils.isEmpty(url)) {
            UnimpededBannerBean banner = new UnimpededBannerBean();
            banner.setTargetPath(url);
            banner.setTitle("优惠页面");
            clickItemData(banner);
        } else
            toastShort("url地址为空");
    }

    /**
     * 点击处理事件
     */
    public void clickItemData(UnimpededBannerBean bannerBean) {
        if (bannerBean != null && !TextUtils.isEmpty(bannerBean.getTargetPath())) {
            String path = bannerBean.getTargetPath();
            if (path.contains("http")) {//启动公司自己html
                MainRouter.gotoWebHtmlActivity(getContext(), bannerBean.getTitle(), path);
            } else if (path.equals("native_app_recharge")) {//加油充值
                MainRouter.gotoRechargeActivity(getActivity());
            } else if (path.equals("native_app_loan")) {

            } else if (path.equals("native_app_toast")) {//敬请期待
                toastShort("此功能开发中,敬请期待~");
            } else if (path.equals("native_app_daijia")) {//代驾
                enterDrivingActivity();
            } else if (path.equals("native_app_enhancement")) {//科目强化
                MainRouter.gotoSubjectActivity(getActivity(), 0);
            } else if (path.equals("native_app_sparring")) {//陪练
                MainRouter.gotoSparringActivity(getActivity(), 0);
            } else if (path.equals("native_app_drive_share")) {//分享
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.share_position_extra, 1);
                Act.getInstance().gotoLoginByIntent(getActivity(), ShareParentActivity.class, intent);
            } else if (path.equals("native_app_car_beauty")) {//汽车美容
                Act.getInstance().gotoIntentLogin(getActivity(), CarBeautyActivity.class);
            } else if (path.equals("native_app_driver")) {//驾校报名
                MainRouter.gotoFahrschuleActivity(getActivity(), 0);
            } else if (path.equals("native_app_yearCheckMap")) {//年检地图
                enterMapActivity();
            } else if (path.equals("native_app_oilStation")) {//优惠加油站
                showOilContacts();
            } else if (path.equals("native_app_endorsement")) {//违章缴费记录
                licenseCheckGrade(2);
            } else if (path.equals("native_app_drivingLicense")) {//驾驶证查分
                licenseCheckGrade(1);
            } else if (path.equals("native_app_97recharge")) {//97加油
                MainRouter.gotoDiscountOilActivity(getActivity());
            } else if (path.equals("native_app_97buyCard")) {//97加油购卡
                MainRouter.gotoBidOilActivity(getActivity());
            } else {//其他
                toastShort("此版本暂无此状态页面,请更新最新版本");
            }
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

    public TextView mCountTv;

    public TextView mmCloseTv;

    @Override
    public void indexLayerSucceed(IndexLayerResponse result) {
        IndexLayerBean bean = result.getData();
        if (bean == null) return;
        String imgUrl = bean.getImgUrl();
        String pageUrl = bean.getPageUrl();
        if (TextUtils.isEmpty(imgUrl) && !TextUtils.isEmpty(pageUrl)) {
            MainRouter.gotoWebHtmlActivity(getContext(), "优惠活动", pageUrl);
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

    private void gotoCapture() {
        MainRouter.gotoCaptureActivity(getActivity());
    }

    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        gotoCapture();
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("相机权限被拒绝，请手机设置中打开");
    }


    /**
     * 进入地图年检页面
     */
    public void enterMapActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 4000, new String[]{
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

    /**
     * 加油地图
     */
    public void showOilContacts() {
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

    protected void licenseCheckGrade(int position) {
        String grade = com.tzly.ctcyh.router.util.SPUtils.instance().getString(com.tzly.ctcyh.router.util.SPUtils.USER_GRADE);
        LicenseFileNumDTO fromJson = null;
        if (!TextUtils.isEmpty(grade)) {
            fromJson = new Gson().fromJson(grade, LicenseFileNumDTO.class);
        } else if (!TextUtils.isEmpty(MainRouter.getUserFilenum()) &&
                !TextUtils.isEmpty(MainRouter.getUserGetdate())) {
            fromJson = new LicenseFileNumDTO();
            fromJson.setFilenum(MainRouter.getUserFilenum());
            fromJson.setStrtdt(MainRouter.getUserGetdate());
        }

        if (fromJson != null && position == 2) {
            MainRouter.gotoPaymentActivity(getActivity(), new Gson().toJson(fromJson));
        } else if (fromJson != null && position == 1) {
            MainRouter.gotoLicenseDetailActivity(getActivity(), new Gson().toJson(fromJson));
        } else {
            MainRouter.gotoLicenseGradeActivity(getActivity(), position);
        }
    }

    /**
     * 进入代驾页面
     */
    public void enterDrivingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoDriving();
        }
    }

    private void gotoDriving() {
        MainRouter.gotoDrivingActivity(getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        gotoDriving();
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        toastShort("您已关闭定位权限,请手机设置中打开");
    }


    @PermissionSuccess(requestCode = 3000)
    public void doOilMapSuccess() {
        gotoOilMap();
    }


    @PermissionFail(requestCode = 3000)
    public void doOilMapFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

    @PermissionSuccess(requestCode = 4000)
    public void doMapSuccess() {
        gotoMap();
    }

    @PermissionFail(requestCode = 4000)
    public void doMapFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }
}
