package com.zantong.mobilecttx.card.activity;

import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.OnClick;

/**
 * 办卡进度
 */
public class CardFlowActivity extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_card_flow;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("办卡进度");
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void DestroyViewAndThing() {
    }

    @OnClick(R.id.tv_web)
    public void onClick(View v) {
        PublicData.getInstance().webviewUrl = "http://www.sh.icbc.com.cn";
        PublicData.getInstance().webviewTitle = "办卡进度查询";
        PublicData.getInstance().isCheckLogin = false;
        Act.getInstance().gotoIntent(this, BrowserActivity.class);
    }


}
