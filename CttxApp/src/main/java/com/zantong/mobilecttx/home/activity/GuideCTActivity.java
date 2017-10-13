package com.zantong.mobilecttx.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.widght.GuideHeaderViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.AppUtils;

/**
 * 引导页面
 */
public class GuideCTActivity extends BaseJxActivity implements GuideHeaderViewPager.GuideInterface {

    @Bind(R.id.guide_headerview)
    GuideHeaderViewPager mGuideHeaderview;
    @Bind(R.id.guide_open)
    TextView mGuideOpen;

    public static final String GUIDE_PIC = "guide_pic_url";
    int mVersionCode;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {}

    protected boolean isNeedCustomTitle() {
        return true;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_ct_guide;
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void initFragmentView(View view) {}

    @Override
    protected void initViewStatus() {
        initData();
    }

    public void initData() {
        Beta.checkUpgrade(false, true);
        Beta.init(getApplicationContext(), false);

        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        int appCode = AppUtils.getAppVersionCode();//当前手机的
        mVersionCode = appCode;
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
        }

        Bundle bundle = getIntent().getExtras();
        ArrayList<StartPicBean> arrayList;
        if (bundle != null)
            arrayList = bundle.getParcelableArrayList(GUIDE_PIC);
        else
            arrayList = new ArrayList<>();

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_open:
                gotoActivity();
                break;
        }
    }

    private void gotoActivity() {
        MobclickAgent.onEvent(this, Config.getUMengID(0));
        Act.getInstance().gotoIntent(this, HomeMainActivity.class);
        SPUtils.getInstance().setIsGuide(String.valueOf(mVersionCode));
        finish();
    }

    @Override
    protected void DestroyViewAndThing() {
    }
}
