package com.zantong.mobilecttx.guide_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.AppUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 */
public class GuideCTActivity extends JxBaseActivity
        implements GuideHeaderViewPager.GuideInterface, View.OnClickListener {

    GuideHeaderViewPager mHeaderViewPager;
    TextView mGuideOpen;

    public static final String GUIDE_PIC = "guide_pic_url";
    int mVersionCode;

    ArrayList<StartPicBean> arrayList;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            arrayList = bundle.getParcelableArrayList(GUIDE_PIC);
        else
            arrayList = new ArrayList<>();
    }

    protected boolean isCustomTitle() {
        return true;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_ct_guide;
    }

    @Override
    protected void bindContentView(View childView) {
        initView(childView);
    }

    public void initView(View childView) {
        mHeaderViewPager = (GuideHeaderViewPager) childView.findViewById(R.id.guide_headerview);
        mGuideOpen = (TextView) childView.findViewById(R.id.guide_open);
        mGuideOpen.setOnClickListener(this);
    }

    @Override
    protected void initContentData() {
        initData();
    }

    public void initData() {
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        mVersionCode = AppUtils.getAppVersionCode();
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
        }

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
        mHeaderViewPager.setImageUrls(guideS, ImageView.ScaleType.FIT_XY, this, mList);
    }

    @Override
    public void getIndex(int index) {
        if (index == 1) {
            mGuideOpen.setVisibility(View.VISIBLE);
        } else {
            mGuideOpen.setVisibility(View.GONE);
        }
    }
    
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_open:
                gotoActivity();
                break;
        }
    }

    private void gotoActivity() {
        MainRouter.gotoMainActivity(this, 0);

        SPUtils.getInstance().setIsGuide(String.valueOf(mVersionCode));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
