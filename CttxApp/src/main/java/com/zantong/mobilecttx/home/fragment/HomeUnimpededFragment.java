package com.zantong.mobilecttx.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.alicloudpush.PushBean;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.daijia.activity.DrivingActivity;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
import com.zantong.mobilecttx.eventbus.BenDiCarInfoEvent;
import com.zantong.mobilecttx.eventbus.GetMsgAgainEvent;
import com.zantong.mobilecttx.eventbus.UpdateCarInfoEvent;
import com.zantong.mobilecttx.home.activity.CaptureActivity;
import com.zantong.mobilecttx.home.activity.HomeMainActivity;
import com.zantong.mobilecttx.home.adapter.HorizontalCarViolationAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.adapter.MainBannerImgHolderView;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.home.bean.HomeBean;
import com.zantong.mobilecttx.home.bean.HomeCarResult;
import com.zantong.mobilecttx.home.bean.HomeNotice;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.interf.IUnimpededFtyContract;
import com.zantong.mobilecttx.map.activity.BaiduMapActivity;
import com.zantong.mobilecttx.presenter.home.UnimpededFtyPresenter;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.bean.MessageCountBean;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.LicenseCheckGradeActivity;
import com.zantong.mobilecttx.weizhang.activity.LicenseDetailActivity;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.widght.MainScrollUpAdvertisementView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;
import cn.qqtheme.framework.widght.HorizontalCarViewPager;
import cn.qqtheme.framework.widght.banner.CBViewHolderCreator;
import cn.qqtheme.framework.widght.banner.ConvenientBanner;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 畅通主页面
 */
public class HomeUnimpededFragment extends BaseRefreshJxFragment
        implements View.OnClickListener,
        IUnimpededFtyContract.IUnimpededFtyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    /**
     * 加油充值
     */
    private TextView mTvOil;
    /**
     * 年检
     */
    private TextView mTvCheck;
    /**
     * 洗车
     */
    private TextView mTvCarwash;
    /**
     * 代驾
     */
    private TextView mTvDrive;
    private ImageView mImgTrumpet;
    private ImageView mImgLabel;

    private MainScrollUpAdvertisementView mCustomGrapevine;
    private HorizontalCarViewPager mCustomViolation;
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

    public static HomeUnimpededFragment newInstance() {
        return new HomeUnimpededFragment();
    }

    public static HomeUnimpededFragment newInstance(String param1, String param2) {
        HomeUnimpededFragment fragment = new HomeUnimpededFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        initView(view);

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
        mTvOil = (TextView) view.findViewById(R.id.tv_oil);
        mTvOil.setOnClickListener(this);
        mTvCheck = (TextView) view.findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(this);
        mTvCarwash = (TextView) view.findViewById(R.id.tv_carwash);
        mTvCarwash.setOnClickListener(this);
        mTvDrive = (TextView) view.findViewById(R.id.tv_drive);
        mTvDrive.setOnClickListener(this);
        mImgTrumpet = (ImageView) view.findViewById(R.id.img_trumpet);
        mImgLabel = (ImageView) view.findViewById(R.id.img_label);
        mCustomGrapevine = (MainScrollUpAdvertisementView) view.findViewById(R.id.custom_grapevine);
        mCustomViolation = (HorizontalCarViewPager) view.findViewById(R.id.custom_violation);

        //简单滑动冲突解决
        mCustomViolation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getPullRefreshView().setPullDownEnable(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        getPullRefreshView().setPullDownEnable(true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void setPresenter(IUnimpededFtyContract.IUnimpededFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
    }

    private void resumeDataVisible() {
        mPresenter.homePage();

        if (PublicData.getInstance().loginFlag) {
            mPresenter.getTextNoticeInfo();
        } else {
            getLocalCarInfo();
        }
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
        EventBus.getDefault().removeStickyEvent(UpdateCarInfoEvent.class);
        EventBus.getDefault().removeStickyEvent(BenDiCarInfoEvent.class);
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
    public void homePageSucceed(HomeResult result) {
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
    public void getTextNoticeInfo(HomeCarResult result) {
        if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();
        if (result != null && result.getData() != null) {
            List<UserCarInfoBean> infoBeanList = result.getData();
//TODO 车辆数据保存处理
            PublicData.getInstance().payData.clear();
            PublicData.getInstance().mCarNum = infoBeanList.size();
            PublicData.getInstance().mServerCars = decodeCarInfo(infoBeanList);

            mUserCarInfoBeanList.addAll(infoBeanList);
        }
        //违章车辆
//        HorizontalCarViolationAdapter carViolationAdapter =
//                new HorizontalCarViolationAdapter(getContext(), mUserCarInfoBeanList);
//        mCustomViolation.setAdapter(carViolationAdapter);

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

            PublicData.getInstance().payData.clear();
            PublicData.getInstance().mCarNum = infoBeanList.size();
            PublicData.getInstance().mServerCars = decodeCarInfo(infoBeanList);

            mUserCarInfoBeanList.addAll(infoBeanList);
        }
        //违章车辆
        mCarViolationAdapter.notifyDataSetChanged(mUserCarInfoBeanList);
        mCustomViolation.notifyDataSetChanged();
    }

    /**
     * 车辆数据解密
     */
    private List<UserCarInfoBean> decodeCarInfo(List<UserCarInfoBean> carInfoBeanList) {
        List<UserCarInfoBean> arrayList = new ArrayList<>();
        for (UserCarInfoBean userCarInfoBean : carInfoBeanList) {
            userCarInfoBean.setEnginenum(Des3.decode(userCarInfoBean.getEnginenum()));
            userCarInfoBean.setCarnum(Des3.decode(userCarInfoBean.getCarnum()));
            userCarInfoBean.setCarframenum(Des3.decode(userCarInfoBean.getCarframenum()));

            if (!TextUtils.isEmpty(userCarInfoBean.getIspaycar())
                    && userCarInfoBean.getIspaycar().equals("1"))

                PublicData.getInstance().payData.add(userCarInfoBean);
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

//        HorizontalCarViolationAdapter carViolationAdapter =
//                new HorizontalCarViolationAdapter(getContext(), mUserCarInfoBeanList);
//        mCustomViolation.setAdapter(carViolationAdapter);
        PublicData.getInstance().mLocalCars = list;
        PublicData.getInstance().mCarNum = list != null ? list.size() : 0;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(UpdateCarInfoEvent event) {
        if (event.isStatus() && mPresenter != null) {
            mPresenter.getTextNoticeInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(BenDiCarInfoEvent event) {
        if (event.isStatus()) getLocalCarInfo();
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
            dto.setUsrId(RSAUtils.strByEncryption(PublicData.getInstance().userID, true));
            CarApiClient.getUnReadMsgCount(this.getActivity(), dto, new CallBack<MessageCountResult>() {
                @Override
                public void onSuccess(MessageCountResult result) {
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
                GlobalConfig.getInstance().eventIdByUMeng(15);

                Act.getInstance().lauchIntentToLogin(getActivity(), MegTypeActivity.class);
                break;
            case R.id.img_scan://扫描
            case R.id.tv_scan:
                GlobalConfig.getInstance().eventIdByUMeng(17);
                takeCapture();
                break;
            case R.id.tv_oil://加油
                GlobalConfig.getInstance().eventIdByUMeng(2);

                Act.getInstance().lauchIntentToLogin(this.getActivity(), RechargeActivity.class);
                break;
            case R.id.tv_check://年检
                GlobalConfig.getInstance().eventIdByUMeng(4);

                PublicData.getInstance().mapType = BaiduMapActivity.TYPE_NIANJIAN;
                enterDrivingActivity();
                break;
            case R.id.tv_carwash://驾驶证查分
                GlobalConfig.getInstance().eventIdByUMeng(7);

                LicenseFileNumDTO bean = SPUtils.getInstance(
                ).getLicenseFileNumDTO();
                if (!PublicData.getInstance().loginFlag ||
                        bean == null && TextUtils.isEmpty(PublicData.getInstance().filenum)) {
                    Act.getInstance().lauchIntentToLogin(getActivity(), LicenseCheckGradeActivity.class);
                } else if (bean != null || !TextUtils.isEmpty(PublicData.getInstance().filenum)
                        && !TextUtils.isEmpty(PublicData.getInstance().getdate)) {
                    LicenseFileNumDTO loginBean = new LicenseFileNumDTO();
                    loginBean.setFilenum(PublicData.getInstance().filenum);
                    loginBean.setStrtdt(PublicData.getInstance().getdate);

                    Intent intent = new Intent(getActivity(), LicenseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(LicenseCheckGradeActivity.KEY_BUNDLE, bean != null ? bean : loginBean);
                    bundle.putBoolean(LicenseCheckGradeActivity.KEY_BUNDLE_FINISH, true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Act.getInstance().lauchIntentToLogin(getActivity(), LicenseCheckGradeActivity.class);
                }
                break;
            case R.id.tv_drive://洗车
                GlobalConfig.getInstance().eventIdByUMeng(9);

                PublicData.getInstance().webviewUrl = Config.HOME_CAR_WASH_URL;
                PublicData.getInstance().webviewTitle = "洗车";
                PublicData.getInstance().isCheckLogin = true;
                Act.getInstance().gotoIntent(this.getActivity(), BrowserActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 进入年检页面
     */
    public void enterDrivingActivity() {
        if (!PublicData.getInstance().loginFlag) {
            Act.getInstance().lauchIntentToLogin(getActivity(), DrivingActivity.class);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            Act.getInstance().lauchIntentToLogin(getActivity(), BaiduMapActivity.class);
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
            Act.getInstance().lauchIntent(getActivity(), CaptureActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        Act.getInstance().lauchIntent(getActivity(), CaptureActivity.class);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("相机权限被拒绝，请手机设置中打开");
    }

    public void setMessageListener(HomeMainActivity.MessageListener messageListener) {
        mHomeMainListener = messageListener;
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        Act.getInstance().lauchIntentToLogin(getActivity(), BaiduMapActivity.class);
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }
}
