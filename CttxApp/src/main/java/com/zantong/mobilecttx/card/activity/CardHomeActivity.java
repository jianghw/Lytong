package com.zantong.mobilecttx.card.activity;

import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.OnClick;

public class CardHomeActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Override
    protected int getContentResId() {
        return R.layout.activity_card_home;
    }

    @Override
    public void initView() {
        setEnsureText("办卡须知");
    }

    @Override
    public void initData() {
        setTitleText("我的畅通卡");
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @OnClick({R.id.card_home_layout1, R.id.card_home_layout2, R.id.card_home_layout3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_home_layout1://绑定畅通卡
                MobclickAgent.onEvent(this, Config.getUMengID(13));
                Act.getInstance().lauchIntent(this, BindJiaZhaoActivity.class);
                break;
            case R.id.card_home_layout2://申办畅通卡
                MobclickAgent.onEvent(this, Config.getUMengID(12));
                Act.getInstance().gotoIntent(this, ApplyCardFirstActivity.class);
                break;
            case R.id.card_home_layout3://办卡进度
                Act.getInstance().gotoIntent(this, CardFlowActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        Act.getInstance().gotoIntent(this, CardNoticeActivity.class);
    }


}
