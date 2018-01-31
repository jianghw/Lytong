package com.zantong.mobile.splash_v;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tzly.annual.base.bean.response.StartPicBean;
import com.tzly.annual.base.util.AppUtils;
import com.tzly.annual.base.util.image.ImageOptions;
import com.zantong.mobile.R;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.contract.ISplashAtyContract;
import com.zantong.mobile.home.bean.StartPicResult;
import com.zantong.mobile.login_v.LoginActivity;
import com.zantong.mobile.main_v.MainClubActivity;
import com.zantong.mobile.presenter.home.SplashPresenter;
import com.zantong.mobile.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.common_splash_activity);

        initView();
        umengChannel();
        initPresenter();
        initThirdPartyData();
    }

    private void initView() {
        mImgLogo = (ImageView) findViewById(R.id.img_logo);
        mImgAdvert = (ImageView) findViewById(R.id.img_advert);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mTvSkip = (TextView) findViewById(R.id.tv_skip);
        if (mTvSkip != null) mTvSkip.setOnClickListener(this);
    }

    private void umengChannel() {
        mImgHuawei = (ImageView) findViewById(R.id.img_huawei);
        String umengChannel = AppUtils.getAppMetaData(getApplicationContext(), "UMENG_CHANNEL");
        if (umengChannel.equals("tzly_huawei")) {
            mImgHuawei.setImageResource(R.mipmap.ic_huawei);
        }
    }

    private void initPresenter() {
        SplashPresenter mPresenter = new SplashPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @Override
    public void setPresenter(ISplashAtyContract.ISplashAtyPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 数据初始
     */
    private void initThirdPartyData() {
        mPresenter.startCountDown();
        mPresenter.readObjectLoginInfoBean();

        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 动画
     */
    public void startAnimation() {
        Animation splashAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_fade);
        splashAnimation.setStartOffset(100);
        splashAnimation.setFillAfter(true);
        mImgLogo.setAnimation(splashAnimation);
        splashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPresenter.startGetPic();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
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
    public void displayAdsImage(StartPicResult result) {
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
    public void displayGuideImage(StartPicResult result) {
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
        Act.getInstance().gotoIntent(this, LoginActivity.class);
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
        mPresenter.unSubscribe();
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
