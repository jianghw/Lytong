package com.zantong.mobilecttx.card.activity;

import android.content.Intent;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.zantong.mobilecttx.R;

public class CardNoticeActivity extends AbstractBaseActivity {

    @Override
    protected void bundleIntent(Intent intent) {}

    @Override
    protected int initContentView() {
        return R.layout.activity_card_notice;
    }

    @Override
    protected void bindFragment() {
        titleContent("办卡须知");
    }

    @Override
    protected void initContentData() {}
}
