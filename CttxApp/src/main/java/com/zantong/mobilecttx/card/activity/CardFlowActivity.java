package com.zantong.mobilecttx.card.activity;

import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.router.MainRouter;

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

        MainRouter.gotoHtmlActivity(this,
                "办卡进度查询", "http://www.sh.icbc.com.cn");
    }


}
