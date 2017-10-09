package com.zantong.mobile.msg_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.annual.base.util.ui.FragmentUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.presenter.MegDetailAtyPresenter;

/**
 * 消息详情页面
 */

public class MegDetailActivity extends BaseJxActivity {

    /**
     * 请求id
     */
    private String messageDetailId;
    private String title;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            messageDetailId = intent.getStringExtra("messageDetailId");
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent(title);

        MegDetailFragment megDetailFragment = MegDetailFragment.newInstance(messageDetailId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentUtils.addFragment(fragmentManager, megDetailFragment, R.id.lay_base_frame, false, true);

        MegDetailAtyPresenter mPresenter = new MegDetailAtyPresenter(
                Injection.provideRepository(getApplicationContext()), megDetailFragment);
    }

    /**
     * 前页面刷新
     */
    public void setResultForRefresh() {
        setResult(MegTypeFragment.MESSAGE_RESULT_CODE);
    }

    @Override
    protected void DestroyViewAndThing() {

    }
}
