package com.zantong.mobilecttx.home.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.alicloudpush.PushBean;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.fragment.BaseFragment;
import com.zantong.mobilecttx.base.fragment.PullableBaseFragment;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.car.fragment.AddCarFragment;
import com.zantong.mobilecttx.car.fragment.BindCarFragment;
import com.zantong.mobilecttx.card.activity.CardHomeActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.common.AppManager;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.daijia.activity.DrivingActivity;
import com.zantong.mobilecttx.eventbus.AddPushTrumpetEvent;
import com.zantong.mobilecttx.eventbus.BenDiCarInfoEvent;
import com.zantong.mobilecttx.eventbus.GetMsgAgainEvent;
import com.zantong.mobilecttx.eventbus.UpdateCarInfoEvent;
import com.zantong.mobilecttx.home.activity.GuideActivity;
import com.zantong.mobilecttx.home.activity.HomeActivity;
import com.zantong.mobilecttx.home.bean.HomeBean;
import com.zantong.mobilecttx.home.bean.HomeNotice;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.bean.VersionResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.home.dto.VersionDTO;
import com.zantong.mobilecttx.huodong.activity.HundredPlanActivity;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.map.activity.BaiduMapActivity;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.user.activity.MineActivity;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DensityUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.OnClickUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.permission.PermissionFail;
import com.zantong.mobilecttx.utils.permission.PermissionGen;
import com.zantong.mobilecttx.utils.permission.PermissionSuccess;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;
import com.zantong.mobilecttx.weizhang.activity.LicenseCheckGradeActivity;
import com.zantong.mobilecttx.weizhang.activity.LicenseDetailActivity;
import com.zantong.mobilecttx.weizhang.activity.ViolationQueryAcitvity;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.widght.AddCarViewPager;
import com.zantong.mobilecttx.widght.HeaderViewPager;
import com.zantong.mobilecttx.widght.MainScrollUpAdvertisementView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.qqtheme.framework.util.LogUtils;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页
 */
public class HomeMvpFragment extends PullableBaseFragment implements View.OnClickListener {

    private ArrayList<BaseFragment> mFragmentList = new ArrayList<>();
    private List<HomeNotice> mList = Collections.synchronizedList(new ArrayList<HomeNotice>());

    private static final String SHOWCASE_ID = "cttx_homefragment";
    private boolean signStatus;//获取报名状态是否成功
    public boolean isCanBack = true;

    InputStream is;
    FileOutputStream fos;
    private final String apkUrl = Environment.getExternalStorageDirectory().getPath() + File.separator + "app_newkey_release_1_3.apk";

    private int onClickRes = 0;
    long currentTm = -1;//當前网络时间

    private TextView mHomeFailRefresh;
    private LinearLayout mHomeCarDots;
    private AddCarViewPager mHomeCarViewpager;
    private ImageView mImgTrumpet;
    private ImageView mImgLabel;
    private MainScrollUpAdvertisementView mHomeScrollUp;
    private ImageView mIllegalQueryImage;
    private LinearLayout mHomeLllegalSearch;
    private ImageView mIllegalQueryImage1;
    private RelativeLayout mHomeCheck;
    private ImageView mPaymentRecordImage;

    private TextView mTextView2;
    private RelativeLayout mHomeWash;
    private ImageView mSweepImage2;
    private LinearLayout mHomeDiscount;
    private ImageView mSweepImage;
    private LinearLayout mHomeLllegalHistory;
    private ImageView mHomeRechargeImage;
    private LinearLayout mHomeRecharge;
    private ImageView mHomeDaijiaImage;
    private RelativeLayout mHomeDaijia;
    private ImageView mPaymentRecordImage3;
    private LinearLayout mHomeMine;
    private TextView mHomeBottomFailRefresh;
    private HeaderViewPager mHomeHeaderview;
    private FrameLayout mHomeBottomLayout;

    public static HomeMvpFragment newInstance() {
        return new HomeMvpFragment();
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_home_mvp;
    }

    /**
     * 可下拉刷新
     *
     * @return true
     */
    @Override
    protected boolean isRefresh() {
        return true;
    }

    /**
     * 不可加载更多
     *
     * @return false
     */
    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected void onRefreshData() {
        if (mHomeHeaderview != null) mHomeHeaderview.setAutoPlay(false);
        getHomeData();
        if (PublicData.getInstance().loginFlag) {
            updateCarInfo(0);
        } else {
            bendiCarInfo(0);
        }
    }

    /**
     * 加载本地数据
     *
     * @param bendi
     */
    private void bendiCarInfo(int bendi) {
        if (!mFragmentList.isEmpty()) mFragmentList.clear();

        List<CarInfoDTO> list = SPUtils.getInstance(this.getActivity()).getCarsInfo();
        PublicData.getInstance().mLocalCars = list;
        PublicData.getInstance().mCarNum = list.size();
        if (bendi == 1) {
            if (list.size() > 0) {
                for (CarInfoDTO dto : list) {
                    mFragmentList.add(
                            new BindCarFragment(
                                    dto.getCarnum(), dto.getCarnumtype(), dto.getEnginenum(),
                                    "--", "--", "--"));
                }
            }
            if (list.size() < 3) {
                mFragmentList.add(new AddCarFragment());
            }
            mHomeCarViewpager.refreshData(mFragmentList);
        } else {
            if (list.size() < 3) {
                mFragmentList.add(0, new AddCarFragment());
            }
            if (list.size() > 0) {
                for (CarInfoDTO dto : list) {
                    mFragmentList.add(
                            0,
                            new BindCarFragment(
                                    dto.getCarnum(), dto.getCarnumtype(), dto.getEnginenum(),
                                    "--", "--", "--"));
                }
            }
            mHomeCarViewpager.refreshData(mFragmentList);
        }
    }

    /**
     * 加载网络数据
     *
     * @param update
     */
    public void updateCarInfo(final int update) {

        UserCarsDTO params = new UserCarsDTO();
        params.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getCarInfo(this.getActivity(), params, new CallBack<UserCarsResult>() {
            @Override
            public void onSuccess(UserCarsResult result) {
                if (!mFragmentList.isEmpty()) mFragmentList.clear();

                if ("000000".equals(result.getSYS_HEAD().getReturnCode())) {
                    PublicData.getInstance().mCarNum = result.getRspInfo().getUserCarsInfo().size();
                    mHomeFailRefresh.setVisibility(View.GONE);
                    if (update == 1) {
                        if (result.getRspInfo().getUserCarsInfo() != null) {
                            if (result.getRspInfo().getUserCarsInfo().size() != 0) {
                                PublicData.getInstance().mServerCars = listU(result.getRspInfo().getUserCarsInfo());
                                PublicData.getInstance().mCarNum = result.getRspInfo().getUserCarsInfo().size();
                                int len = result.getRspInfo().getUserCarsInfo().size();

                                Collections.sort(result.getRspInfo().getUserCarsInfo(), new Comparator<UserCarInfoBean>() {

                                    /*
                                     * int compare(Student o1, Student o2) 返回一个基本类型的整型，
                                     * 返回负数表示：o1 小于o2，
                                     * 返回0 表示：o1和o2相等，
                                     * 返回正数表示：o1大于o2。
                                     */
                                    public int compare(UserCarInfoBean o1, UserCarInfoBean o2) {

                                        //按照学生的年龄进行升序排列
                                        if (o1.getIspaycar().equals("0") && o2.getIspaycar().equals("1")) {
                                            return 1;
                                        }
                                        return -1;
                                    }
                                });

                                if (len > 3) {
                                    len = 3;
                                }
                                PublicData.getInstance().payData.clear();
                                for (int i = 0; i < len; i++) {
                                    UserCarInfoBean info = result.getRspInfo().getUserCarsInfo().get(i);
                                    mFragmentList.add(
                                            new BindCarFragment(
                                                    info.getCarnum(),
                                                    info.getCarnumtype(),
                                                    info.getEnginenum(),
                                                    info.getUntreatcount(),
                                                    StringUtils.getPriceString(info.getUntreatamt()),
                                                    info.getUntreatcent()));
                                    if (info.getIspaycar().equals("1")) {
                                        PublicData.getInstance().payData.add(info);//保存可缴费车辆
                                    }
                                }
                            }
                            if (result.getRspInfo().getUserCarsInfo().size() < 3) {
                                mFragmentList.add(new AddCarFragment());
                            }
                        }
                        mHomeCarViewpager.refreshData(mFragmentList);
                    } else {
                        if (result.getRspInfo().getUserCarsInfo() != null) {
                            PublicData.getInstance().mServerCars = listU(result.getRspInfo().getUserCarsInfo());
                            if (result.getRspInfo().getUserCarsInfo().size() < 3) {
                                mFragmentList.add(0, new AddCarFragment());
                            }
                            if (result.getRspInfo().getUserCarsInfo().size() != 0) {
                                int len = result.getRspInfo().getUserCarsInfo().size();
                                if (len > 3) {
                                    len = 3;
                                }
                                for (int i = 0; i < len; i++) {
                                    UserCarInfoBean info = result.getRspInfo().getUserCarsInfo().get(i);
                                    mFragmentList.add(
                                            0,
                                            new BindCarFragment(
                                                    info.getCarnum(), info.getCarnumtype(), info.getEnginenum(),
                                                    info.getUntreatcount(),
                                                    StringUtils.getPriceString(info.getUntreatamt()),
                                                    info.getUntreatcent()));
                                }
                            }
                        }
                        mHomeCarViewpager.refreshData(mFragmentList);
                    }
                } else {
                    mHomeFailRefresh.setText("加载不给力？点击重试");
                    mHomeFailRefresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);

                mHomeFailRefresh.setText("加载不给力？点击重试");
                mHomeFailRefresh.setVisibility(View.VISIBLE);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(UpdateCarInfoEvent event) {
        if (event.isStatus()) {
            updateCarInfo(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(BenDiCarInfoEvent event) {
        if (event.isStatus()) {
            bendiCarInfo(0);
        }
    }

    /**
     * 标记小喇叭
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataSynEvent(AddPushTrumpetEvent event) {
        LogUtils.e("onDataSynEvent");
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
            dto.setUsrId(RSAUtils.strByEncryption(this.getActivity(), PublicData.getInstance().userID, true));
            CarApiClient.getUnReadMsgCount(this.getActivity(), dto, new CallBack<MessageCountResult>() {
                @Override
                public void onSuccess(MessageCountResult result) {
                    if (result.getResponseCode() == 2000) {
                        HomeActivity activity = (HomeActivity) getActivity();
                        if (activity != null) {
                            activity.messageCount(result.getData() != null ? result.getData().getCount() : 0);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onLoadMoreData() {
    }

    @Override
    protected void initFragmentView(View view) {
        mHomeFailRefresh = (TextView) view.findViewById(R.id.home_fail_refresh);
        mHomeFailRefresh.setOnClickListener(this);
        mHomeCarDots = (LinearLayout) view.findViewById(R.id.home_car_dots);
        mHomeCarViewpager = (AddCarViewPager) view.findViewById(R.id.home_car_viewpager);
        mImgTrumpet = (ImageView) view.findViewById(R.id.img_trumpet);
        mImgLabel = (ImageView) view.findViewById(R.id.img_label);
        mHomeScrollUp = (MainScrollUpAdvertisementView) view.findViewById(R.id.home_scroll_up);
        mIllegalQueryImage = (ImageView) view.findViewById(R.id.illegal_query_image);
        mHomeLllegalSearch = (LinearLayout) view.findViewById(R.id.home_lllegal_search);
        mHomeLllegalSearch.setOnClickListener(this);
        mIllegalQueryImage1 = (ImageView) view.findViewById(R.id.illegal_query_image1);
        mHomeCheck = (RelativeLayout) view.findViewById(R.id.home_check);
        mHomeCheck.setOnClickListener(this);
        mPaymentRecordImage = (ImageView) view.findViewById(R.id.payment_record_image);
        mTextView2 = (TextView) view.findViewById(R.id.textView2);
        mHomeWash = (RelativeLayout) view.findViewById(R.id.home_wash);
        mHomeWash.setOnClickListener(this);
        mSweepImage2 = (ImageView) view.findViewById(R.id.sweep_image2);
        mHomeDiscount = (LinearLayout) view.findViewById(R.id.home_discount);
        mHomeDiscount.setOnClickListener(this);
        mSweepImage = (ImageView) view.findViewById(R.id.sweep_image);
        mHomeLllegalHistory = (LinearLayout) view.findViewById(R.id.home_lllegal_history);
        mHomeLllegalHistory.setOnClickListener(this);
        mHomeRechargeImage = (ImageView) view.findViewById(R.id.home_recharge_image);
        mHomeRecharge = (LinearLayout) view.findViewById(R.id.home_recharge);
        mHomeRecharge.setOnClickListener(this);
        mHomeDaijiaImage = (ImageView) view.findViewById(R.id.home_daijia_image);
        mHomeDaijia = (RelativeLayout) view.findViewById(R.id.home_daijia);
        mHomeDaijia.setOnClickListener(this);
        mPaymentRecordImage3 = (ImageView) view.findViewById(R.id.payment_record_image3);
        mHomeMine = (LinearLayout) view.findViewById(R.id.home_mine);
        mHomeMine.setOnClickListener(this);
        mHomeBottomFailRefresh = (TextView) view.findViewById(R.id.home_bottom_fail_refresh);
        mHomeBottomFailRefresh.setOnClickListener(this);
        mHomeHeaderview = (HeaderViewPager) view.findViewById(R.id.home_headerview);
        mHomeBottomLayout = (FrameLayout) view.findViewById(R.id.home_bottom_layout);

        mHomeCarViewpager.setFragmentList(mFragmentList, this.getFragmentManager());
        mHomeHeaderview.setFocusable(true);

        //定义mHomeBottomLayout布局的高宽
        int screenWidth = DensityUtils.getScreenWidth(this.getActivity());
        int screenHeight = DensityUtils.getScreenHeight(this.getActivity());
        ViewGroup.LayoutParams layoutParams = mHomeBottomLayout.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth * 4 / 9;
        mHomeBottomLayout.setLayoutParams(layoutParams);
        //是否显示引导页面
        if (!SPUtils.getInstance(getActivity()).getGuideSaoFaDan()) {
            PublicData.getInstance().GUIDE_TYPE = 0;
            Act.getInstance().gotoIntent(this.getActivity(), GuideActivity.class);
        }
    }

    @Override
    protected void loadingData() {
        //登录信息
        if (PublicData.getInstance().loginFlag) liyingreg();
        //清空布局数据
        if (!mFragmentList.isEmpty()) mFragmentList.clear();
        //违章车辆
        if (PublicData.getInstance().loginFlag) {
            updateCarInfo(1);
        } else {
            bendiCarInfo(1);
        }
        //mHeaderViewPager.setAutoScroll();
        //小喇叭
        initScrollUp(new ArrayList<HomeNotice>());
        //利盈通数据
        getHomeData();
        //版本更新
        initAppLatestVersion();
    }

    /**
     * 版本更新
     */
    private void initAppLatestVersion() {
        VersionDTO params = new VersionDTO();
        params.setVersionType("0");
        UserApiClient.getCurrentVerson(getActivity(), params, new CallBack<VersionResult>() {
            @Override
            public void onSuccess(VersionResult result) {
                if (result.getData() != null) {
                    int versionFlag = Tools.compareVersion(Tools.getVerName(getActivity()), result.getData().getVersion());
                    if (versionFlag == -1) {
                        isCanBack = false;
                        showUpdateDialog(result);
                    }
                }
            }

        });
    }

    private void showUpdateDialog(final VersionResult result) {
        DialogUtils.updateDialog(
                getActivity(),
                "版本升级",
                "请升级到最新版本(" + result.getData().getVersion() + ")，以免影响您的使用体验",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//暂不升级
                        if (result.getData().getIsUpdate() == 1) {
                            AppManager.getAppManager().AppExit(getActivity(), false);
                        }
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isCanBack = true;
                        //检查权限
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //如果没有授权，则请求授权
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            downloadDemo(result.getData().getAddress());
                        }

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomeScrollUp != null) mHomeScrollUp.start();
        new Thread(networkTask).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHomeScrollUp != null) mHomeScrollUp.stop();
    }

    private List<UserCarInfoBean> listU(List<UserCarInfoBean> listUcar) {
        List<UserCarInfoBean> listUcb = new ArrayList<UserCarInfoBean>();
        for (UserCarInfoBean userCarInfoBean : listUcar) {
            userCarInfoBean.setEnginenum(Des3.decode(userCarInfoBean.getEnginenum()));
            userCarInfoBean.setCarnum(Des3.decode(userCarInfoBean.getCarnum()));
            userCarInfoBean.setCarframenum(Des3.decode(userCarInfoBean.getCarframenum()));
            listUcb.add(userCarInfoBean);
        }
        return listUcb;
    }

    private void initScrollUp(final List<HomeNotice> mDataLists) {
        if (mDataLists != null && mDataLists.size() == 0) {
            mDataLists.add(new HomeNotice("-1", 0, "暂无通知"));
        }
        mHomeScrollUp.setData(mDataLists);
        mHomeScrollUp.setTextSize(12);
        mHomeScrollUp.setTimer(5000);
        mHomeScrollUp.start();
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_lllegal_search://违章查询
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(2));
                Act.getInstance().lauchIntent(this.getActivity(), ViolationQueryAcitvity.class);
                break;
            case R.id.home_check://驾驶证查分
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(35));

                LicenseFileNumDTO bean = SPUtils.getInstance(
                        getActivity().getApplicationContext()).getLicenseFileNumDTO();
                if (!PublicData.getInstance().loginFlag ||
                        bean == null && TextUtils.isEmpty(PublicData.getInstance().filenum)) {
                    Act.getInstance().lauchIntentToLogin(this.getActivity(), LicenseCheckGradeActivity.class);
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
                    Act.getInstance().lauchIntentToLogin(this.getActivity(), LicenseCheckGradeActivity.class);
                }
                break;
            case R.id.home_wash://洗车
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(4));

                PublicData.getInstance().webviewUrl = Config.HOME_CAR_WASH_URL;
                PublicData.getInstance().webviewTitle = "洗车";
                PublicData.getInstance().isCheckLogin = true;
                Act.getInstance().gotoIntent(this.getActivity(), BrowserActivity.class);
                break;
            case R.id.home_discount://优惠活动
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(8));
                if (PublicData.getInstance().loginFlag) {
                    getSignStatus();
                } else {
                    Act.getInstance().gotoIntent(this.getActivity(), LoginActivity.class);
                }
                break;
            case R.id.home_lllegal_history://开通畅通卡
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(3));
                if (PublicData.getInstance().loginFlag) {
                    if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {
                        Act.getInstance().lauchIntentToLogin(this.getActivity(), CardHomeActivity.class);
                    } else {
                        Act.getInstance().lauchIntentToLogin(this.getActivity(), MyCardActivity.class);
                    }
                } else {
                    Act.getInstance().gotoIntent(this.getActivity(), LoginActivity.class);
                }
                break;
            case R.id.home_recharge://加油充值
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(6));

                Act.getInstance().lauchIntentToLogin(this.getActivity(), RechargeActivity.class);
                break;
            case R.id.home_daijia://代驾
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(7));

                if (PublicData.getInstance().loginFlag) {
                    onClickRes = 1;
                    showContacts();
                } else {
                    Act.getInstance().gotoIntent(this.getActivity(), LoginActivity.class);
                }
                break;
            case R.id.home_mine://我的
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(9));

                Act.getInstance().gotoIntent(this.getActivity(), MineActivity.class);
                break;
            case R.id.home_fail_refresh://老接口刷新
                if (OnClickUtils.isFastDoubleClick()) {
                    mHomeFailRefresh.setText("刷新加载中...");
                    updateCarInfo(1);
                } else {
                    ToastUtils.showShort(this.getActivity(), "亲，您点的太快了");
                }
                break;
            case R.id.home_bottom_fail_refresh://新接口刷新
                if (OnClickUtils.isFastDoubleClick()) {
                    mHomeBottomFailRefresh.setText("刷新加载中...");
                    getHomeData();
                } else {
                    ToastUtils.showShort(this.getActivity(), "亲，您点的太快了");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取同赞利盈首页数据
     */
    private void getHomeData() {
        HomeDataDTO params = new HomeDataDTO();
        String userId = RSAUtils.strByEncryptionLiYing(getActivity(), PublicData.getInstance().userID, true);
        params.setUserId(userId);

        CarApiClient.getHomeData(this.getActivity(), params, new CallBack<HomeResult>() {
            @Override
            public void onSuccess(HomeResult result) {
                if (result.getResponseCode() == 2000) {
                    displayBottomAdverState(false);

                    HomeBean bean = result.getData();
                    //未读消息
                    HomeActivity activity = (HomeActivity) getActivity();
                    if (activity != null) {
                        activity.messageCount(bean != null ? bean.getMsgNum() : 0);
                    }
                    //小喇叭通知
                    if (bean != null && bean.getNotices() != null) {
                        mHomeScrollUp.setData(bean.getNotices());

                        if (!mList.isEmpty()) mList.clear();
                        mList.addAll(bean.getNotices());
                    }
                    //广告页面
                    if (bean != null && bean.getAdvertisementResponse() != null) {
                        mHomeHeaderview.setImageUrls(bean.getAdvertisementResponse(), ImageView.ScaleType.FIT_XY);
                        mHomeHeaderview.setAutoPlay(true);
                    }
                } else {
                    displayBottomAdverState(true);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                displayBottomAdverState(true);
            }
        });
    }

    private void displayBottomAdverState(boolean isDisplay) {
        mHomeBottomFailRefresh.setVisibility(isDisplay ? View.VISIBLE : View.GONE);
        mHomeBottomFailRefresh.setText("加载不给力？点击重试");
    }

    /**
     * 小喇叭更新通知
     */
    public synchronized void updateNoticeMessage(PushBean bean) {
        List<HomeNotice> notices = new ArrayList<>();
        if (mList.isEmpty() || mList == null) {
            notices.add(new HomeNotice(bean.getId(), 3, bean.getContent(), bean.isNewMeg()));
        } else {
            String newId = bean.getId();
            for (HomeNotice notice : mList) {
                if (notice.getId().equals(newId)) {
                    notice.setNewMeg(bean.isNewMeg());
                } else {
                    notices.add(new HomeNotice(bean.getId(), 3, bean.getContent(), bean.isNewMeg()));
                    break;
                }
            }
            notices.addAll(mList);
        }
        mHomeScrollUp.setData(notices);

        if (!mList.isEmpty()) mList.clear();
        mList.addAll(notices);
    }


    /**
     * 点击更新消息
     */
    private void clickUpdateData(String key, HomeNotice homeNotice) {
        try {
            List<HomeNotice> listHomeNotice = new ArrayList<HomeNotice>();
            List<HomeNotice> listCommom = (List<HomeNotice>) UserInfoRememberCtrl.readObject(getActivity(), key);
            if (listCommom != null) {
                for (HomeNotice homeNot : listCommom) {
                    if (homeNot.getDesc().equals(homeNotice.getDesc())
                            && homeNot.getId() == homeNotice.getId()
                            && homeNot.getType() == homeNotice.getType()) {
                        homeNot.setNewMeg(false);
                    }
                    listHomeNotice.add(homeNot);
                }
                UserInfoRememberCtrl.saveObject(getActivity(), key, listHomeNotice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取本地的通知的数据（年检，违章，加油 ）
     */
    private List<HomeNotice> noticeList() {
        List<HomeNotice> listHomeNotice = new ArrayList<HomeNotice>();
        try {
            List<HomeNotice> listNianJian = (List<HomeNotice>) UserInfoRememberCtrl
                    .readObject(getActivity(), "nianjian");
            List<HomeNotice> listJiaYou = (List<HomeNotice>) UserInfoRememberCtrl
                    .readObject(getActivity(), "youjia");
            if (listNianJian != null) {
                listHomeNotice.addAll(listByDate("nianjian", listNianJian));
                LogUtils.i("meg", "===========listNianJian=========>" + listNianJian.size());
            }

            if (listJiaYou != null) {
                listHomeNotice.addAll(listByDate("youjia", listJiaYou));
            }

            if (PublicData.getInstance().loginFlag) {
                List<HomeNotice> listWeiZahng = (List<HomeNotice>) UserInfoRememberCtrl
                        .readObject(getActivity(), PublicData.getInstance().userID);
                if (listWeiZahng != null) {
                    listHomeNotice.addAll(listByDate(PublicData.getInstance().userID, listWeiZahng));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHomeNotice;
    }

    public void downloadDemo(final String url) {
        final ProgressDialog progressBar = new ProgressDialog(getActivity());

        Retrofit2Utils retrofit2Utils = new Retrofit2Utils();
        final FileDownloadApi api = retrofit2Utils.getRetrofitHttps("http://192.9.210.176:8080/").create(FileDownloadApi.class);
        Observable.create(new Observable.OnSubscribe<Long[]>() {
            @Override
            public void call(Subscriber<? super Long[]> subscriber) {
                try {
//                    String urls = "http://7b1g8u.com1.z0.glb.clouddn.com/app_newkey_release_8_4.apk";
                    Response<ResponseBody> response = api.downloadFileWithFixedUrl(url).execute();
                    try {
                        if (response != null && response.isSuccessful()) {
                            //文件总长度
                            long fileSize = response.body().contentLength();
                            long fileSizeDownloaded = 0;
                            is = response.body().byteStream();
                            File file = new File(apkUrl);
                            if (file.exists()) {
                                file.delete();
                            } else {
                                file.createNewFile();
                            }
                            fos = new FileOutputStream(file);
                            int count = 0;
                            byte[] buffer = new byte[1024];
                            while ((count = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, count);
                                fileSizeDownloaded += count;
                                Long[] data = new Long[2];
                                data[0] = fileSizeDownloaded;
                                data[1] = fileSize;
                                subscriber.onNext(data);
                            }
                            fos.flush();
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new Exception("接口请求异常"));
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.e("why", e.toString());
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long[]>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "文件下载完成");
//                        PublicData.getInstance().mNetLocationBean = bean;apkUrl
                        Tools.installApk(getActivity(), apkUrl);
                        progressBar.dismiss();
                        AppManager.getAppManager().AppExit(getActivity(), false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(getActivity(), "网络错误，请重试");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long[] data) {
                        Log.d("MainActivity", data[0] + "");
                        progressBar.setCancelable(true);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        int demo = (int) (data[0] * 100 / data[1]);
                        progressBar.setProgress(demo);
                        progressBar.setMax(100);
                        progressBar.setCanceledOnTouchOutside(false);
                        progressBar.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    return true;
                                }
                                return false;
                            }
                        });
                        progressBar.show();

                    }
                });
    }

    /**
     * @return
     */
    private List<HomeNotice> listByDate(String key, List<HomeNotice> homeNoticeList) {
        List<HomeNotice> noticeList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(Tools.getYearDate());
            for (HomeNotice homeNotice : homeNoticeList) {
                Date dateH = sdf.parse(homeNotice.getDate());
                if (date.getTime() <= dateH.getTime()) {
                    noticeList.add(homeNotice);
                }
            }
            UserInfoRememberCtrl.saveObject(getActivity(), key, noticeList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return noticeList;
    }

    private void liyingreg() {
        try {
            String phone = RSAUtils.strByEncryptionLiYing(this.getActivity(), PublicData.getInstance().mLoginInfoBean.getPhoenum(), true);
            SHATools sha = new SHATools();
            String pwd = RSAUtils.strByEncryptionLiYing(this.getActivity(),
                    SHATools.hexString(sha.eccryptSHA1(SPUtils.getInstance(getActivity()).getUserPwd())), true);
            LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
            liYingRegDTO.setPhoenum(phone);
            liYingRegDTO.setPswd(pwd);
            liYingRegDTO.setUsrid(RSAUtils.strByEncryptionLiYing(this.getActivity(), PublicData.getInstance().mLoginInfoBean.getUsrid(), true));
            CarApiClient.liYingReg(this.getActivity(), liYingRegDTO, new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取报名状态
     */
    private void getSignStatus() {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(this.getActivity(), activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {
                signStatus = true;
                //报名结束时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                long endTm = 0;
                try {
                    endTm = sdf.parse(DateUtils.START_TIME).getTime();//毫秒
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentTm == -1) {
                    ToastUtils.showShort(HomeMvpFragment.this.getActivity(), "拉取网络时间失败");
                    return;
                }
                LogUtils.i("当前时间戳：" + currentTm + "，当前时间：" + currentTm + "，报名截止时间戳" + endTm);

                if (result.getResponseCode() == 2000 && !TextUtils.isEmpty(result.getData().getPlateNo())) {
                    SPUtils.getInstance(HomeMvpFragment.this.getActivity()).setSignStatus(true);
                    SPUtils.getInstance(HomeMvpFragment.this.getActivity()).setSignCarPlate(result.getData().getPlateNo());

                    if (currentTm < endTm) {//4.17号之前
                        MobclickAgent.onEvent(HomeMvpFragment.this.getActivity(), Config.getUMengID(22));
                        Act.getInstance().lauchIntentToLogin(HomeMvpFragment.this.getActivity(), HundredPlanActivity.class);
                    } else {//4.17号之后
                        PublicData.getInstance().webviewUrl = Config.HUNDRED_PLAN_HOME;
                        PublicData.getInstance().webviewTitle = "百日无违章";
                        Act.getInstance().lauchIntentToLogin(HomeMvpFragment.this.getActivity(), BrowserActivity.class);
                    }
                }
                if (result.getResponseCode() == 4000) {
                    SPUtils.getInstance(HomeMvpFragment.this.getActivity()).setSignStatus(false);
                    if (currentTm < endTm) {//4.17号之前
                        MobclickAgent.onEvent(HomeMvpFragment.this.getActivity(), Config.getUMengID(19));
                        Act.getInstance().lauchIntentToLogin(HomeMvpFragment.this.getActivity(), HundredPlanActivity.class);
                    } else {//4.17号之后
                        PublicData.getInstance().webviewUrl = Config.HUNDRED_PLAN_DEADLINE;
                        PublicData.getInstance().webviewTitle = "百日无违章";
                        Act.getInstance().lauchIntentToLogin(HomeMvpFragment.this.getActivity(), BrowserActivity.class);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                signStatus = false;
            }
        });
    }

    public void showContacts() {
        PermissionGen.needPermission(this, 100,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                }
        );
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        if (onClickRes == 0) {
            Act.getInstance().gotoIntent(this.getActivity(), BaiduMapActivity.class);
        } else if (onClickRes == 1) {
            Act.getInstance().gotoIntent(this.getActivity(), DrivingActivity.class);
        }

    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.showShort(this.getActivity(), "您已关闭定位权限");
    }

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 取得资源对象
            URL url = null;
            URLConnection uc = null;
            try {
                url = new URL("http://www.baidu.com");
                // 生成连接对象
                uc = url.openConnection();
                uc.setConnectTimeout(1000);
                uc.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long time = uc.getDate();

            Message msg = new Message();
            Bundle data = new Bundle();
            data.putLong("webTm", time);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            currentTm = data.getLong("webTm");
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHomeHeaderview != null) mHomeHeaderview.close();
        if (!mList.isEmpty()) mList.clear();
        if (!mFragmentList.isEmpty()) mFragmentList.clear();
        EventBus.getDefault().removeStickyEvent(AddPushTrumpetEvent.class);
        EventBus.getDefault().removeStickyEvent(GetMsgAgainEvent.class);
        EventBus.getDefault().removeStickyEvent(UpdateCarInfoEvent.class);
        EventBus.getDefault().removeStickyEvent(BenDiCarInfoEvent.class);
    }
}
