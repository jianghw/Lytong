package com.zantong.mobilecttx.splash_v;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.image.ImageOptions;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.DensityUtils;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.animation.PropertyUtils;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.home.bean.StartPicResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.splash_p.ISplashAtyContract;
import com.zantong.mobilecttx.splash_p.SplashPresenter;
import com.zantong.mobilecttx.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动页
 */
public class SplashActivity extends AbstractBaseActivity
        implements ISplashAtyContract.ISplashAtyView, View.OnClickListener {

    private ISplashAtyContract.ISplashAtyPresenter mPresenter;

    private ImageView mImgLogo;
    private ImageView mImgAdvert;
    private TextView mTvCount;
    private TextView mTvSkip;
    private ImageView mImgHuawei;
    /**
     * 引导页图片地址
     */
    private ArrayList<StartPicBean> mResultList = new ArrayList<>();
    private TextView mTvName;

    /**
     * 是否要子布局定义title
     */
    protected boolean isCustomTitle() {
        return true;
    }

    @Override
    protected int initContentView() {
        return R.layout.main_activity_splash;
    }

    @Override
    protected void bundleIntent(Intent intent) {
    }

    @Override
    protected void bindFragment() {
        initView();

        mTvName.setText(getResources().getString(R.string.main_app_name));
        String channel = com.tzly.ctcyh.router.util.SPUtils.instance().getString(com.tzly.ctcyh.router.util.SPUtils.APP_CHANNEL);
        mTvName.setVisibility(channel.contains("tzly") ? View.GONE : View.VISIBLE);
        mImgHuawei.setVisibility(channel.contains("huawei")||channel.contains("Huawei") ? View.VISIBLE : View.GONE);

        SplashPresenter mPresenter = new SplashPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    private void initView() {
        mImgLogo = (ImageView) findViewById(R.id.img_logo);
        mImgAdvert = (ImageView) findViewById(R.id.img_advert);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mTvName = (TextView) findViewById(R.id.tv_name);

        mTvSkip = (TextView) findViewById(R.id.tv_skip);
        if (mTvSkip != null) mTvSkip.setOnClickListener(this);

        mImgHuawei = (ImageView) findViewById(R.id.img_huawei);
    }

    @Override
    public void setPresenter(ISplashAtyContract.ISplashAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initContentData() {
        initThirdPartyData();

        int width = DensityUtils.getScreenWidth(this);
        int height = DensityUtils.getScreenHeight(this);
        LogUtils.e("===》" + width + "/" + height);
    }

    /**
     * 数据初始
     */
    private void initThirdPartyData() {
        if (mPresenter != null) mPresenter.startCountDown();

        if (mPresenter != null) mPresenter.updateToken();

        startAnimation();
    }

    /**
     * 动画
     */
    public void startAnimation() {
        ObjectAnimator animator = PropertyUtils.createShakeView(mImgLogo, 1000, 1);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mPresenter != null) mPresenter.startGetPic();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    @Override
    public void countDownOver() {
        gotoMain();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void countDownTextView(long l) {
        mTvCount.setText(l + "s");
    }

    /**
     * 广告图片显示
     */
    @Override
    public void displayAdsImage(StartPicResponse result) {
        List<StartPicBean> mList = result.getData();

        String url = "";
        if (mList != null && mList.size() > 0) {
            StartPicBean bean = mList.get(0);
            url = bean.getPicUrl();
        }
        ImageLoader.getInstance().displayImage(url, mImgAdvert,
                ImageOptions.getSplashOptions(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        displaySkipTv(true);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        displaySkipTv(true);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        displaySkipTv(true);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        displaySkipTv(true);
                    }
                });
    }

    @Override
    public void displayAdsImageError(String message) {
        mImgAdvert.setImageResource(R.mipmap.ic_splash_default);
        displaySkipTv(true);
    }

    /**
     * 添加引导页面图片
     */
    @Override
    public void displayGuideImage(StartPicResponse result) {
        mResultList.addAll(result.getData());
    }

    public void displaySkipTv(boolean isDisplay) {
        mTvSkip.setVisibility(isDisplay ? View.VISIBLE : View.GONE);
        if (isDisplay) mTvSkip.bringToFront();
    }

    /**
     * 版本比较 页面跳转
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public void gotoMain() {
        int appCode = AppUtils.getAppVersionCode();//当前手机的

        String guide = SPUtils.getInstance().getIsGuide();
        int versionCode = 1;//保存版本
        try {
            versionCode = Integer.valueOf(guide);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (appCode <= versionCode) {
            MainRouter.gotoMainActivity(this, 0);
            //            MainRouter.gotoPaySucceedActivity(this, "2");
            MobclickAgent.onEvent(this, Config.getUMengID(0));
        } else {
            MainRouter.gotoGuideActivity(this, mResultList);
            overridePendingTransition(0, 0);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                gotoMain();
                break;
            default:
                break;
        }
    }
}
