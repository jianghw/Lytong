package com.zantong.mobilecttx.card.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.jumptools.Act;

/**
 * 没有绑定畅通卡时的状态页面
 */
public class UnblockedCardActivity extends AbstractBaseActivity implements View.OnClickListener {

    /**
     * 绑定畅通卡
     */
    private TextView mTvBound;
    /**
     * 申办畅通卡
     */
    private TextView mTvBid;
    /**
     * 申办进度
     */
    private TextView mTvPlan;

    @Override
    protected void bundleIntent(Intent intent) {}

    @Override
    protected int initContentView() {
        return R.layout.activity_unblocked_card;
    }

    @Override
    protected void bindFragment() {
        titleContent("我的畅通卡");
        titleServer();

        initView();
    }

    @Override
    protected void initContentData() {}

    /**
     * 右title
     */
    @Override
    protected void rightClickListener() {
        Act.getInstance().gotoIntent(this, CardNoticeActivity.class);
    }

    @Override
    protected void imageClickListener() {
        MainRouter.gotoWebHtmlActivity(this, "客服",
                "http://h5.liyingtong.com/mot/faq/changtongka.html");
    }

    public void initView() {
        mTvBound = (TextView) findViewById(R.id.tv_bound);
        mTvBound.setOnClickListener(this);
        mTvBid = (TextView) findViewById(R.id.tv_bid);
        mTvBid.setOnClickListener(this);
        mTvPlan = (TextView) findViewById(R.id.tv_plan);
        mTvPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
    }
}
