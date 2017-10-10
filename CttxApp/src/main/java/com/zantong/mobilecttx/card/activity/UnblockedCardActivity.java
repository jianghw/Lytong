package com.zantong.mobilecttx.card.activity;

import android.os.Bundle;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.OnClick;

/**
 * 没有绑定畅通卡时的状态页面
 */
public class UnblockedCardActivity extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_unblocked_card;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("我的畅通卡");

        setTvRightVisible("办卡须知");
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    /**
     * 右title
     */
    @Override
    protected void rightClickListener() {
        Act.getInstance().gotoIntent(this, CardNoticeActivity.class);
    }

    @OnClick({R.id.tv_bound, R.id.tv_bid, R.id.tv_plan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bound://绑定畅通卡
                MobclickAgent.onEvent(this, Config.getUMengID(13));
                Act.getInstance().lauchIntent(this, BindJiaZhaoActivity.class);
                break;
            case R.id.tv_bid://申办畅通卡
                MobclickAgent.onEvent(this, Config.getUMengID(12));
                Act.getInstance().gotoIntent(this, ApplyCardFirstActivity.class);
                break;
            case R.id.tv_plan://办卡进度
                Act.getInstance().gotoIntent(this, CardFlowActivity.class);
                break;
            default:
                break;
        }
    }

}
