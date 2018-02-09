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
import com.tzly.annual.base.bean.response.AnnouncementBean;
import com.tzly.annual.base.bean.response.AnnouncementResult;
import com.tzly.annual.base.custom.image.CircleImageView;
import com.tzly.annual.base.custom.trumpet.TrumpetListView;
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
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.browser.BrowserHtmlActivity;
import com.zantong.mobile.code_v.AlipayCodeActivity;
import com.zantong.mobile.code_v.WeixinCodeActivity;
import com.zantong.mobile.common.activity.CommonProblemActivity;
import com.zantong.mobile.contract.IUnimpededFtyContract;
import com.zantong.mobile.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobile.fahrschule.activity.SparringActivity;
import com.zantong.mobile.fahrschule.activity.SubjectActivity;
import com.zantong.mobile.home.activity.Codequery;
import com.zantong.mobile.home.bean.HomeBean;
import com.zantong.mobile.home.bean.HomeResult;
import com.zantong.mobile.map.activity.BaiduMapParentActivity;
import com.zantong.mobile.model.repository.LocalData;
import com.zantong.mobile.msg_v.MegTypeActivity;
import com.zantong.mobile.order.activity.CattleOrderActivity;
import com.zantong.mobile.order.activity.MyOrderActivity;
import com.zantong.mobile.main_p.UnimpededFtyPresenter;
import com.zantong.mobile.share_v.DtShareActivity;
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

    private TrumpetListView mCustomTrumpet;

    private LinearLayout mLayOil;
    private LinearLayout mLayAnnual;
    private LinearLayout mLayViolations;

    private IUnimpededFtyContract.IUnimpededFtyPresenter mPresenter;

    private LinearLayout mLayOrder;
    private LinearLayout mLayShare;
    private LinearLayout mLayProblem;

    @Override
    protected void initContentData() {
        UnimpededFtyPresenter mPresenter = new UnimpededFtyPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);
        //小喇叭
        initScrollUp();
    }

    private void initScrollUp() {
        List<AnnouncementBean> mList = new ArrayList<>();
        mList.add(new AnnouncementBean(-1, "暂无通知消息", -1));
        mCustomTrumpet.setData(mList);
    }

    @Override
    protected void userRefreshContentData() {
        mPresenter.findAnnouncements();
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
        upGradeInfo();

        DrawerLayout drawer = (DrawerLayout) childView.findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(this);
        NavigationView navigationView = (NavigationView) childView.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //显示item 图标色
        navigationView.setItemIconTintList(null);
        //获取头布局文件
        View headerView = navigationView.getHeaderView(0);
        mUserImage = (CircleImageView) headerView.findViewById(R.id.img_user);

        mUserText = (TextView) headerView.findViewById(R.id.tv_user);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LogUtils.e("widthPixels===" + metrics.widthPixels);
        LogUtils.e("heightPixels===" + metrics.heightPixels);

        int mStatusBarColor = getResources().getColor(R.color.colorPrimaryDark);
        StatusBarUtils.setColorForDrawerLayout(this, drawer, mStatusBarColor, StatusBarUtils.DEFAULT_BAR_ALPHA);

        initTitle(childView);
        initView(childView);
    }

    private void upGradeInfo() {//更新检查
        //更新检查
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
                DialogUtils.updateDialog(this,
                        upgradeInfo.title, upgradeInfo.newFeature,
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

        if (LocalData.getInstance().isLogin()) {
            String phoenum = LocalData.getInstance().getUserPhone();
            if (!TextUtils.isEmpty(phoenum) && phoenum.length() >= 7)
                mUserText.setText(phoenum.substring(7));
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
        mCustomTrumpet = (TrumpetListView) view.findViewById(R.id.custom_trumpet);

        mLayOil = (LinearLayout) view.findViewById(R.id.lay_oil);
        mLayOil.setOnClickListener(this);
        mLayAnnual = (LinearLayout) view.findViewById(R.id.lay_annual);
        mLayAnnual.setOnClickListener(this);
        mLayViolations = (LinearLayout) view.findViewById(R.id.lay_violations);
        mLayViolations.setOnClickListener(this);

        mLayOrder = (LinearLayout) view.findViewById(R.id.lay_order);
        mLayOrder.setOnClickListener(this);
        mLayShare = (LinearLayout) view.findViewById(R.id.lay_share);
        mLayShare.setOnClickListener(this);
        mLayProblem = (LinearLayout) view.findViewById(R.id.lay_problem);
        mLayProblem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lay_oil) {//加油充值
            Act.getInstance().gotoIntent(this, WeixinCodeActivity.class);
        } else if (id == R.id.lay_annual) {//爱车估值
            Act.getInstance().gotoIntent(this, AlipayCodeActivity.class);
        } else if (id == R.id.lay_violations) {//违章查询
            Act.getInstance().gotoIntent(this, ViolationActivity.class);
        } else if (id == R.id.lay_order) {
            gotoCattleOrder();
        } else if (id == R.id.lay_share) {
            Act.getInstance().gotoIntentLogin(this, DtShareActivity.class);
        } else if (id == R.id.lay_problem) {
            Act.getInstance().gotoIntent(this, CommonProblemActivity.class);
        }
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
    }

    @Override
    public void setPresenter(IUnimpededFtyContract.IUnimpededFtyPresenter presenter) {
        mPresenter = presenter;
    }

    private void startCampaignCustom(boolean hidden) {
        if (hidden) {//不可见时
            if (mCustomTrumpet != null) mCustomTrumpet.stop();
            //停止翻页
        } else {
            if (mCustomTrumpet != null && !mCustomTrumpet.isRunning()) mCustomTrumpet.start();
        }
    }

    /**
     * 内部通知数据
     */
    @Override
    public void announcementsError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void announcementsSucceed(AnnouncementResult result) {
        List<AnnouncementBean> lis = result.getData();
        mCustomTrumpet.setData(lis);
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
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        userLoginInfo();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}
