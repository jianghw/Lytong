package com.zantong.mobile.splash_v;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.annual.base.JxBaseActivity;
import com.tzly.annual.base.bean.response.StartPicBean;
import com.tzly.annual.base.custom.viewpager.GuideViewPager;
import com.tzly.annual.base.custom.viewpager.IGuideViewPager;
import com.tzly.annual.base.util.AppUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.main_v.MainClubActivity;
import com.zantong.mobile.utils.SPUtils;
import com.zantong.mobile.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 */
public class GuideCTActivity extends JxBaseActivity
        implements IGuideViewPager, View.OnClickListener {

    GuideViewPager mGuideHeaderview;
    TextView mGuideOpen;

    public static final String GUIDE_PIC = "guide_pic_url";
    int mVersionCode;

    /**
     * 是否自定义状态栏效果
     */
    protected boolean isStatusBar() {
        return false;
    }

    /**
     * 是否要子布局定义title
     */
    protected boolean isCustomTitle() {
        return true;
    }

    @Override
    protected void initContentData() {
        initData();
    }

    /**
     * 显示内容
     */
    protected int initContentView() {
        return R.layout.activity_ct_guide;
    }

    @Override
    protected void bindContentView(View childView) {
        initView(childView);
    }

    public void initData() {
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        int appCode = AppUtils.getAppVersionCode();//当前手机的
        mVersionCode = appCode;
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
        }

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

    public void initView(View childView) {
        mGuideHeaderview = (GuideViewPager) childView.findViewById(R.id.guide_headerview);
        mGuideOpen = (TextView) childView.findViewById(R.id.guide_open);
        mGuideOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_open:
                gotoActivity();
                break;
        }
    }

    private void gotoActivity() {
        Act.getInstance().gotoIntent(this, MainClubActivity.class);
        SPUtils.getInstance().setIsGuide(String.valueOf(mVersionCode));
        finish();
    }
}
