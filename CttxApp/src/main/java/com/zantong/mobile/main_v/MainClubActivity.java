package com.zantong.mobile.main_v;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.annual.base.RefreshBaseActivity;
import com.tzly.annual.base.bean.HomeNotice;
import com.tzly.annual.base.custom.ScrollDrawerLayout;
import com.tzly.annual.base.custom.banner.CBViewHolderCreator;
import com.tzly.annual.base.custom.banner.ConvenientBanner;
import com.tzly.annual.base.custom.image.CircleImageView;
import com.tzly.annual.base.custom.trumpet.TrumpetScrollUpView;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.AppUtils;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.FileUtils;
import com.tzly.annual.base.util.LogUtils;
import com.tzly.annual.base.util.StatusBarUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.image.ImageLoadUtils;
import com.tzly.annual.base.util.primission.PermissionFail;
import com.tzly.annual.base.util.primission.PermissionGen;
import com.tzly.annual.base.util.primission.PermissionSuccess;
import com.zantong.mobile.R;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.browser.BrowserHtmlActivity;
import com.zantong.mobile.chongzhi.activity.RechargeActivity;
import com.zantong.mobile.common.activity.CommonProblemActivity;
import com.zantong.mobile.contract.IUnimpededFtyContract;
import com.zantong.mobile.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobile.fahrschule.activity.SparringActivity;
import com.zantong.mobile.fahrschule.activity.SubjectActivity;
import com.zantong.mobile.home.activity.Codequery;
import com.zantong.mobile.home.bean.HomeAdvertisement;
import com.zantong.mobile.home.bean.HomeBean;
import com.zantong.mobile.home.bean.HomeResult;
import com.zantong.mobile.main_v.adapter.LocalImageHolder;
import com.zantong.mobile.main_v.adapter.MainBannerHolder;
import com.zantong.mobile.map.activity.BaiduMapParentActivity;
import com.zantong.mobile.msg_v.MegTypeActivity;
import com.zantong.mobile.order.activity.CattleOrderActivity;
import com.zantong.mobile.order.activity.MyOrderActivity;
import com.zantong.mobile.presenter.home.UnimpededFtyPresenter;
import com.zantong.mobile.user.activity.AboutActivity;
import com.zantong.mobile.user.activity.ProblemFeedbackActivity;
import com.zantong.mobile.user.activity.SettingActivity;
import com.zantong.mobile.user.activity.UserInfoUpdate;
import com.zantong.mobile.user.bean.RspInfoBean;
import com.zantong.mobile.utils.DialogUtils;
import com.zantong.mobile.utils.Tools;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.weizhang.activity.ViolationActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 畅通卡车友会
 */
public class MainClubActivity extends RefreshBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, IUnimpededFtyContract.IUnimpededFtyView, DrawerLayout.DrawerListener {

    private ImageView mImgBack;
    private TextView mTvTitle;
    private ImageView mImgHome;
    private CircleImageView mUserImage;
    private TextView mUserText;

    private ConvenientBanner mCustomBanner;
    private ConvenientBanner mCustomBanner_2;
    private TrumpetScrollUpView mCustomTrumpet;

    private LinearLayout mLayOil;
    private ImageView mImgOil;
    private TextView mTvOil;
    private LinearLayout mLayAnnual;
    private ImageView mImgAnnual;
    private TextView mTvAnnual;
    private LinearLayout mLayViolations;
    private ImageView mImgViolations;
    private TextView mTvViolations;
    private LinearLayout mLayApply;
    private ImageView mImgApply;
    private TextView mTvApply;
    private LinearLayout mLaySparring;
    private ImageView mImgSparring;
    private TextView mTvSparring;
    private LinearLayout mLaySubject;
    private ImageView mImgSubject;
    private TextView mTvSubject;

    private IUnimpededFtyContract.IUnimpededFtyPresenter mPresenter;
    /**
     * 小喇叭数据
     */
    private List<HomeNotice> mHomeNotices = Collections.synchronizedList(new ArrayList<HomeNotice>());

    @Override
    protected void initContentData() {
        UnimpededFtyPresenter mPresenter = new UnimpededFtyPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);

        //广告页本地加载
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.mipmap.default_330_160);
        mCustomBanner.setPages(
                new CBViewHolderCreator<LocalImageHolder>() {
                    @Override
                    public LocalImageHolder createHolder() {
                        return new LocalImageHolder();
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.mipmap.ic_banner_2);
        mCustomBanner_2.setPages(
                new CBViewHolderCreator<LocalImageHolder>() {
                    @Override
                    public LocalImageHolder createHolder() {
                        return new LocalImageHolder();
                    }
                }, bannerImages)
                .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        //小喇叭
        initScrollUp(mHomeNotices);
    }

    private void initScrollUp(List<HomeNotice> mDataLists) {
        if (mDataLists != null && mDataLists.size() == 0) {
            List<HomeNotice> mList = new ArrayList<>();
            mList.add(new HomeNotice("-1", 0, "暂无通知"));
            mCustomTrumpet.setData(mList);
        } else {
            mCustomTrumpet.setData(mDataLists);
        }
        mCustomTrumpet.setTextSize(12);
        mCustomTrumpet.setTimer(5000);
    }

    @Override
    protected void userRefreshContentData() {
        mPresenter.homePage();
    }

    /**
     * 自定义状态栏
     */
    protected boolean isStatusBar() {
        return false;
    }

    /**
     * 自定义图标
     */
    protected boolean isCustomTitle() {
        return true;
    }

    /**
     * 不用页面加载效果
     */
    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int initChildView() {
        return R.layout.activity_main_club;
    }

    /**
     * 是否自定义状态栏效果
     */
    @Override
    protected void bindChildView(View childView) {
        ScrollDrawerLayout drawer = (ScrollDrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //显示item 图标色
        navigationView.setItemIconTintList(null);
        //获取头布局文件
        View headerView = navigationView.getHeaderView(0);
        mUserImage = (CircleImageView) headerView.findViewById(R.id.img_user);
        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUserInfo();
            }
        });
        mUserText = (TextView) headerView.findViewById(R.id.tv_user);

        //        RelativeLayout statusBar = (RelativeLayout) findViewById(R.id.lay_re);
        //        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        //        layoutParams.height = StatusBarUtils.getStatusBarHeight(getApplicationContext());
        //        statusBar.setLayoutParams(layoutParams);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LogUtils.e("widthPixels===" + metrics.widthPixels);
        LogUtils.e("heightPixels===" + metrics.heightPixels);
        int mStatusBarColor = getResources().getColor(R.color.colorPrimaryDark);
        StatusBarUtils.setColorForDrawerLayout(this, drawer, mStatusBarColor, StatusBarUtils.DEFAULT_BAR_ALPHA);

        initTitle(childView);
        initView(childView);

        upGradeInfo();
    }

    private void upGradeInfo() {//更新检查
        Beta.checkUpgrade(false, true);
        Beta.init(getApplicationContext(), false);

        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        int appCode = AppUtils.getAppVersionCode();
        int mVersionCode = appCode;
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
        }
        //  public int upgradeType = 1;//升级策略 1建议 2强制 3手工
        if (appCode < mVersionCode) {
            if (upgradeInfo.upgradeType == 2) {
                Beta.checkUpgrade();
            } else {
                DialogUtils.updateDialog(this, upgradeInfo.title, upgradeInfo.newFeature,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Beta.checkUpgrade();
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareAppShop(ContextUtils.getContext().getPackageName());
                            }
                        });
            }
        }
    }

    /**
     * 根据应用包名，跳转到应用市场
     *
     * @param packageName 所需下载（评论）的应用包名
     */
    public void shareAppShop(String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.toastShort("您没有安装应用市场,请点击立即更新");
        }
    }

    private void gotoUserInfo() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Act.getInstance().gotoIntentLogin(this, UserInfoUpdate.class);
    }

    /**
     * 用户登录信息初始化
     */
    private void userLoginInfo() {
        RspInfoBean infoBean = MemoryData.getInstance().mLoginInfoBean;

        File file = getHeadImageFile(mUserImage);
        if (file == null && infoBean != null) {
            ImageLoadUtils.loadHead(infoBean.getPortrait(), mUserImage);
        }

        if (infoBean != null && !Tools.isStrEmpty(infoBean.getNickname())) {
            mUserText.setText(infoBean.getNickname());
        } else if (infoBean != null) {
            String phoenum = infoBean.getPhoenum();
            if (!TextUtils.isEmpty(phoenum) && phoenum.length() >= 7)
                mUserText.setText(infoBean.getPhoenum().substring(7));
        } else {
            mUserImage.setImageResource(R.mipmap.portrait);
            mUserText.setText("您还未登录");
        }

    }

    private void initTitle(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.lay_re_bg);
        mImgBack = (ImageView) relativeLayout.findViewById(R.id.img_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClickListener();
            }
        });
        mTvTitle = (TextView) relativeLayout.findViewById(R.id.tv_title);
        mImgHome = (ImageView) relativeLayout.findViewById(R.id.img_home);
        mImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClickListener();
            }
        });

        mTvTitle.setText("熊猫小助手");
        mImgBack.setImageResource(R.mipmap.btn_person);
        mImgHome.setVisibility(View.VISIBLE);
        mImgHome.setImageResource(R.mipmap.res_ic_add);
    }

    /**
     * 左边图片点击
     */
    protected void backClickListener() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 右边图片点击
     */
    protected void imageClickListener() {
        popUpMore();
    }

    private void initView(View view) {
        mCustomBanner = (ConvenientBanner) view.findViewById(R.id.custom_banner);
        mCustomBanner_2 = (ConvenientBanner) view.findViewById(R.id.custom_banner_2);

        mCustomTrumpet = (TrumpetScrollUpView) view.findViewById(R.id.custom_trumpet);
        ViewGroup.LayoutParams layoutParams = mCustomTrumpet.getLayoutParams();
        mCustomTrumpet.setTrumpetHeight(layoutParams.height);

        mLayOil = (LinearLayout) view.findViewById(R.id.lay_oil);
        mLayOil.setOnClickListener(this);
        mImgOil = (ImageView) view.findViewById(R.id.img_oil);
        mTvOil = (TextView) view.findViewById(R.id.tv_oil);
        mLayAnnual = (LinearLayout) view.findViewById(R.id.lay_annual);
        mLayAnnual.setOnClickListener(this);
        mImgAnnual = (ImageView) view.findViewById(R.id.img_annual);
        mTvAnnual = (TextView) view.findViewById(R.id.tv_annual);
        mLayViolations = (LinearLayout) view.findViewById(R.id.lay_violations);
        mLayViolations.setOnClickListener(this);
        mImgViolations = (ImageView) view.findViewById(R.id.img_violations);
        mTvViolations = (TextView) view.findViewById(R.id.tv_violations);
        mLayApply = (LinearLayout) view.findViewById(R.id.lay_apply);
        mLayApply.setOnClickListener(this);
        mImgApply = (ImageView) view.findViewById(R.id.img_apply);
        mTvApply = (TextView) view.findViewById(R.id.tv_apply);
        mLaySparring = (LinearLayout) view.findViewById(R.id.lay_sparring);
        mLaySparring.setOnClickListener(this);
        mImgSparring = (ImageView) view.findViewById(R.id.img_sparring);
        mTvSparring = (TextView) view.findViewById(R.id.tv_sparring);
        mLaySubject = (LinearLayout) view.findViewById(R.id.lay_subject);
        mLaySubject.setOnClickListener(this);
        mImgSubject = (ImageView) view.findViewById(R.id.img_subject);
        mTvSubject = (TextView) view.findViewById(R.id.tv_subject);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lay_oil) {//加油充值
            Act.getInstance().gotoIntentLogin(this, RechargeActivity.class);
        } else if (id == R.id.lay_annual) {//爱车估值
            carValuation();
        } else if (id == R.id.lay_violations) {//违章查询
            Act.getInstance().gotoIntent(this, ViolationActivity.class);
        } else if (id == R.id.lay_apply) {//驾校报名
            Act.getInstance().gotoIntentLogin(this, FahrschuleActivity.class);
        } else if (id == R.id.lay_sparring) {//陪练
            Act.getInstance().gotoIntentLogin(this, SparringActivity.class);
        } else if (id == R.id.lay_subject) {
            Act.getInstance().gotoIntentLogin(this, SubjectActivity.class);
        }
    }

    protected void carValuation() {
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.browser_title_extra, "爱车估值");
        intent.putExtra(JxGlobal.putExtra.browser_url_extra, "http://m.jingzhengu.com/xiansuo/sellcar-changtongcheyouhui.html");
        Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
    }

    private void popUpMore() {
        View inflate = getLayoutInflater().inflate(R.layout.popup_club_more, null);
        final PopupWindow popupWindow = new PopupWindow(inflate,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        TextView boxLy = (TextView) inflate.findViewById(R.id.tv_scan);
        TextView infoLy = (TextView) inflate.findViewById(R.id.tv_order_receiving);
        TextView dirLy = (TextView) inflate.findViewById(R.id.tv_order);

        boxLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                gotoQRCodeScan();
            }
        });
        infoLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                gotoCattleOrder();
            }
        });
        dirLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                gotoMyOrder();
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_club_more));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mImgHome, Gravity.RIGHT | Gravity.TOP,
                getResources().getDimensionPixelOffset(R.dimen.x20),
                getResources().getDimensionPixelOffset(R.dimen.y88));
    }

    /**
     * 进入年检页面
     */
    public void enterDrivingActivity() {
        if (!MemoryData.getInstance().loginFlag) {
            Act.getInstance().gotoIntentLogin(this, BaiduMapParentActivity.class);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            Act.getInstance().gotoIntentLogin(this, BaiduMapParentActivity.class);
        }
    }

    private void gotoCattleOrder() {
        Act.getInstance().gotoIntentLogin(this, CattleOrderActivity.class);
    }

    private void gotoMyOrder() {
        Act.getInstance().gotoIntentLogin(this, MyOrderActivity.class);
    }

    private void gotoQRCodeScan() {
        Act.getInstance().gotoIntent(this, Codequery.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_order) {
            gotoMyOrder();
        } else if (id == R.id.nav_msg) {
            Act.getInstance().gotoIntentLogin(this, MegTypeActivity.class);
        } else if (id == R.id.nav_questions) {
            Act.getInstance().gotoIntent(this, CommonProblemActivity.class);
        } else if (id == R.id.nav_service) {
            Act.getInstance().gotoIntent(this, ProblemFeedbackActivity.class);
        } else if (id == R.id.nav_us) {
            Act.getInstance().gotoIntent(this, AboutActivity.class);
        } else if (id == R.id.nav_advertising) {
            gotoBrowser();
        } else if (id == R.id.nav_setting) {
            Act.getInstance().gotoIntentLogin(this, SettingActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoBrowser() {
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.browser_title_extra, "隐私声明");
        intent.putExtra(JxGlobal.putExtra.browser_url_extra, "file:///android_asset/bindcard_agreement.html");
        Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
    }

    @Override
    public void setPresenter(IUnimpededFtyContract.IUnimpededFtyPresenter presenter) {
        mPresenter = presenter;
    }

    private void startCampaignCustom(boolean hidden) {
        if (hidden) {//不可见时
            if (mCustomTrumpet != null) mCustomTrumpet.stop();
            //停止翻页
            if (mCustomBanner != null) mCustomBanner.stopTurning();
            if (mCustomBanner_2 != null) mCustomBanner_2.stopTurning();
        } else {
            if (mCustomTrumpet != null && !mCustomTrumpet.isRunning()) mCustomTrumpet.start();
            //开始自动翻页
            if (mCustomBanner != null && !mCustomBanner.isRunning())
                mCustomBanner.startTurning(4000);
            if (mCustomBanner_2 != null && !mCustomBanner_2.isRunning())
                mCustomBanner_2.startTurning(4000);
        }
    }

    @Override
    public void loadingProgress() {
    }

    @Override
    public void hideLoadingProgress() {
    }

    @Override
    public void homePageError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void homePageSucceed(HomeResult result) {
        HomeBean bean = result.getData();
        //未读消息
        unreadMessage(bean);

        //小喇叭通知
        if (bean != null && bean.getNotices() != null) {
            if (!bean.getNotices().isEmpty())
                mCustomTrumpet.setData(bean.getNotices());

            if (!mHomeNotices.isEmpty()) mHomeNotices.clear();
            mHomeNotices.addAll(bean.getNotices());
        }

        //广告页面
        if (bean != null && bean.getAdvertisementResponse() != null) {
            List<HomeAdvertisement> advertisementResponse = bean.getAdvertisementResponse();
            mCustomBanner.setPages(
                    new CBViewHolderCreator<MainBannerHolder>() {
                        @Override
                        public MainBannerHolder createHolder() {
                            return new MainBannerHolder();
                        }
                    },
                    advertisementResponse)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                    //设置翻页的效果，不需要翻页效果可用不设
                    .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
        }
    }

    private void unreadMessage(HomeBean bean) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_msg);
        menuItem.setTitle(bean != null ?
                bean.getMsgNum() > 0 ? "你有" + bean.getMsgNum() + "条未读消息" : "你的消息"
                : "你的消息");
    }

    /**
     * 头像
     */
    private File getHeadImageFile(ImageView userImage) {
        String ImgPath = FileUtils.photoImagePath(ContextUtils.getContext(), FileUtils.CROP_DIR);

        File mCropFile = new File(ImgPath);
        if (!mCropFile.exists()) {
            return null;
        }
        Uri outputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            outputUri = getUriForFileByN(mCropFile);
        } else {
            outputUri = Uri.fromFile(mCropFile);
        }

        Bitmap bitmap = FileUtils.decodeUriAsBitmap(outputUri, ContextUtils.getContext());
        if (bitmap != null) userImage.setImageBitmap(bitmap);

        return mCropFile;
    }

    private Uri getUriForFileByN(File mCameraFile) {
        try {
            return FileProvider.getUriForFile(ContextUtils.getContext(),
                    getApplication().getPackageName() + ".fileprovider", mCameraFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.fromFile(mCameraFile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        Act.getInstance().gotoIntentLogin(this, BaiduMapParentActivity.class);
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }

    /**
     * DrawerLayout 滑动监听
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {}

    @Override
    public void onDrawerOpened(View drawerView) {
        userLoginInfo();
    }

    @Override
    public void onDrawerClosed(View drawerView) {}

    @Override
    public void onDrawerStateChanged(int newState) {}
}
