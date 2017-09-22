package com.zantong.mobilecttx.cattle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.annual.base.RefreshBaseActivity;
import com.tzly.annual.base.bean.HomeNotice;
import com.tzly.annual.base.custom.ScrollDrawerLayout;
import com.tzly.annual.base.custom.banner.CBViewHolderCreator;
import com.tzly.annual.base.custom.banner.ConvenientBanner;
import com.tzly.annual.base.custom.image.CircleImageView;
import com.tzly.annual.base.custom.trumpet.MainScrollUpAdvertisementView;
import com.tzly.annual.base.util.StatusBarUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.browser.HtmlBrowserActivity;
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.activity.CommonProblemActivity;
import com.zantong.mobilecttx.contract.IUnimpededFtyContract;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule.activity.SparringActivity;
import com.zantong.mobilecttx.fahrschule.activity.SubjectActivity;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.adapter.MainBannerImgHolderView;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.home.bean.HomeBean;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.order.activity.OrderParentActivity;
import com.zantong.mobilecttx.presenter.home.UnimpededFtyPresenter;
import com.zantong.mobilecttx.user.activity.AboutActivity;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.activity.SettingActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;

/**
 * 畅通卡车友会
 */
public class MainClubActivity extends RefreshBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, IUnimpededFtyContract.IUnimpededFtyView {

    private ImageView mImgBack;
    private TextView mTvTitle;
    private ImageView mImgHome;
    private CircleImageView mUserImage;
    private TextView mUserText;

    private com.tzly.annual.base.custom.banner.ConvenientBanner mCustomBanner;
    private com.tzly.annual.base.custom.trumpet.MainScrollUpAdvertisementView mCustomTrumpet;

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
    private NavigationView mNavView;

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
        localImages.add(R.mipmap.banner);
        mCustomBanner.setPages(
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //显示item 图标色
        navigationView.setItemIconTintList(null);
        //获取头布局文件
        View headerView = navigationView.getHeaderView(0);
        mUserImage = (CircleImageView) headerView.findViewById(R.id.img_user);
        mUserText = (TextView) headerView.findViewById(R.id.tv_user);

//        RelativeLayout statusBar = (RelativeLayout) findViewById(R.id.lay_re);
//        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
//        layoutParams.height = StatusBarUtils.getStatusBarHeight(getApplicationContext());
//        statusBar.setLayoutParams(layoutParams);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.e("TAG", "===" + metrics.heightPixels);
        Log.e("TAG", "===" + metrics.widthPixels);
        int mStatusBarColor = getResources().getColor(R.color.colorPrimaryDark);
        StatusBarUtils.setColorForDrawerLayout(this, drawer, mStatusBarColor, StatusBarUtils.DEFAULT_BAR_ALPHA);

        initTitle(childView);
        initView(childView);
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

        mTvTitle.setText("畅通卡车友会");
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
        mCustomTrumpet = (MainScrollUpAdvertisementView) view.findViewById(R.id.custom_trumpet);
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
        mNavView = (NavigationView) view.findViewById(R.id.nav_view);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lay_oil) {//加油充值
            Act.getInstance().gotoIntentLogin(this, RechargeActivity.class);
        } else if (id == R.id.lay_annual) {//年检

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
            }
        });
        dirLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_club_more));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mImgHome, Gravity.RIGHT | Gravity.TOP,
                getResources().getDimensionPixelOffset(R.dimen.x20),
                getResources().getDimensionPixelOffset(R.dimen.y88));
    }

    private void gotoQRCodeScan() {
        Act.getInstance().gotoIntent(this, QRCodeScanActivity.class);
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
            Act.getInstance().gotoIntentLogin(this, OrderParentActivity.class);
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
        Act.getInstance().gotoLoginByIntent(this, HtmlBrowserActivity.class, intent);
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
        } else {
            if (mCustomTrumpet != null && !mCustomTrumpet.isRunning()) mCustomTrumpet.start();
            //开始自动翻页
            if (mCustomBanner != null && !mCustomBanner.isRunning())
                mCustomBanner.startTurning(4000);
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
//        mTvMsgCount.setText(bean != null ? String.valueOf(bean.getMsgNum()) : "0");
//        mTvMsgCount.setVisibility(bean != null ?
//                bean.getMsgNum() == 0 ? View.INVISIBLE : View.VISIBLE
//                : View.INVISIBLE);
//
//        if (mHomeMainListener != null)
//            mHomeMainListener.setTipOfNumber(2, bean != null ? bean.getMsgNum() : 0);

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

}
