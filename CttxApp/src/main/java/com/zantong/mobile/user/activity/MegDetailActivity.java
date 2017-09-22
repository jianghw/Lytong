package com.zantong.mobile.user.activity;

import android.content.Intent;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.MvpBaseActivity;
import com.zantong.mobile.common.Injection;
import com.zantong.mobile.presenter.MegDetailAtyPresenter;
import com.zantong.mobile.user.fragment.MegDetailFragment;
import com.zantong.mobile.user.fragment.MegTypeFragment;

import com.tzly.annual.base.util.AtyUtils;

/**
 * 消息详情页面
 */

public class MegDetailActivity extends MvpBaseActivity {

    /**
     * 请求id
     */
    private String messageDetailId;

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void setTitleView() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            messageDetailId = intent.getStringExtra("messageDetailId");
            setTitleText(title);
        }
    }

    @Override
    protected void initMvPresenter() {
        MegDetailFragment megDetailFragment =
                (MegDetailFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
        if (megDetailFragment == null) {
            megDetailFragment = MegDetailFragment.newInstance(messageDetailId);

            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), megDetailFragment, R.id.lay_base_frame);
        }

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
    protected void onDestroy() {
        super.onDestroy();
    }
}