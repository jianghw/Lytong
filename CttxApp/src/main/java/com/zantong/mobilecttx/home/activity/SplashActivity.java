package com.zantong.mobilecttx.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.home.bean.StartPicResult;
import com.zantong.mobilecttx.interf.ISplashAtyContract;
import com.zantong.mobilecttx.presenter.home.SplashPresenter;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

import static com.zantong.mobilecttx.home.activity.GuideCTActivity.GUIDE_PIC;

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
    /**
     * 引导页图片地址
     */
    private ArrayList<StartPicBean> mResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.common_splash_activity);

        initView();
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
        int version = Tools.compareVersion(
                SPUtils.getInstance().getIsGuide(),
                Tools.getVerName(getApplicationContext()));
        if (version == -1) {//需要更新时
            mResultList = new ArrayList<>();
            mPresenter.startGuidePic();
        }
        mPresenter.startCountDown();
        mPresenter.readObjectLoginInfoBean();
        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onSubscribe();
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
     *
     * @param result
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
     *
     * @param result
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
        int version = Tools.compareVersion(
                SPUtils.getInstance().getIsGuide(),
                Tools.getVerName(getApplicationContext()));
        if (version != -1) {
            MobclickAgent.onEvent(this, Config.getUMengID(0));
            Act.getInstance().gotoIntent(this, HomeMainActivity.class);
            SPUtils.getInstance().setIsGuide(Tools.getVerName(getApplicationContext()));
            finish();
        } else {
            Intent intent = new Intent(this, GuideCTActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(GUIDE_PIC, mResultList);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
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
        }
    }
}
