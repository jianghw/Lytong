package com.zantong.mobilecttx.home.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.SystemBarTintManager;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.widght.GuideHeaderViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 引导页面
 */
public class GuideCTActivity extends BaseActivity implements GuideHeaderViewPager.GuideInterface {

    @Bind(R.id.guide_headerview)
    GuideHeaderViewPager mGuideHeaderview;
    @Bind(R.id.guide_open)
    ImageView mGuideOpen;

    public static final String GUIDE_PIC = "guide_pic_url";

    @Override
    public void initView() {
        setStatusBarColor();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ct_guide;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.trans));
        }
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        ArrayList<StartPicBean> arrayList = bundle.getParcelableArrayList(GUIDE_PIC);
        List<StartPicBean> mList = new ArrayList<>();
        if (arrayList != null && arrayList.size() > 0) {
            for (StartPicBean bean : arrayList) {
                mList.add(bean.getPicNum() - 1, bean);
            }
        }

        List<Integer> guideS = new ArrayList<>();
        guideS.add(R.mipmap.guide_1);
        guideS.add(R.mipmap.guide_2);
        guideS.add(R.mipmap.guide_3);
        mGuideHeaderview.setImageUrls(guideS, ImageView.ScaleType.FIT_XY, this, mList);
    }

    @Override
    public void getIndex(int index) {
        if (index == 1) {
            mGuideOpen.setVisibility(View.VISIBLE);
        } else {
            mGuideOpen.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.guide_open})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.guide_open:
                gotoActivity();
                break;
        }
    }

    private void gotoActivity() {
        MobclickAgent.onEvent(this, Config.getUMengID(0));
        Act.getInstance().gotoIntent(this, HomeActivity.class);
        SPUtils.getInstance().setIsGuide(Tools.getVerName(this));
        finish();
    }

}
