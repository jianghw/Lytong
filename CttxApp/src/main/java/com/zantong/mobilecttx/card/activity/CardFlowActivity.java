package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.browser.BrowserHtmlActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.OnClick;
import cn.qqtheme.framework.global.JxGlobal;

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
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.browser_title_extra, "办卡进度查询");
        intent.putExtra(JxGlobal.putExtra.browser_url_extra, "http://www.sh.icbc.com.cn");
        Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
    }


}
