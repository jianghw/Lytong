package com.zantong.mobilecttx.msg_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;

/**
 * 消息详情页面
 */

public class MegDetailActivity extends JxBaseActivity {

    /**
     * 请求id
     */
    private String messageDetailId;
    private String mTitle;

    /**
     * 前页面刷新
     */

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(MainGlobal.putExtra.meg_title_extra))
                mTitle = bundle.getString(MainGlobal.putExtra.meg_title_extra);
            if (intent.hasExtra(MainGlobal.putExtra.meg_id_extra))
                messageDetailId = bundle.getString(MainGlobal.putExtra.meg_id_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent(mTitle);
    }

    @Override
    protected void initContentData() {
        MegDetailFragment megDetailFragment =
                (MegDetailFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
        if (megDetailFragment == null) {
            megDetailFragment = MegDetailFragment.newInstance(messageDetailId);

            FragmentUtils.add(getSupportFragmentManager(), megDetailFragment, R.id.lay_base_frame, false, true);
        }
    }
}
