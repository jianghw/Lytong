package com.zantong.mobilecttx.msg_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;

/**
 * 消息详情页面
 */

public class MegDetailActivity extends AbstractBaseActivity {

    /**
     * 请求id
     */
    private String messageDetailId;
    private String mTitle;
    private MegDetailFragment megDetailFragment;

    @Override
    protected void bundleIntent(Intent intent) {
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
    protected void bindFragment() {
        titleContent(mTitle);
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (megDetailFragment == null) {
            megDetailFragment = MegDetailFragment.newInstance(messageDetailId);

            FragmentUtils.add(fragmentManager, megDetailFragment, R.id.lay_base_frame);
        }
    }
}
