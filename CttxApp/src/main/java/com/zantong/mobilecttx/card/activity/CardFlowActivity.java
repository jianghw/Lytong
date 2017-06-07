package com.zantong.mobilecttx.card.activity;

import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import butterknife.OnClick;

public class CardFlowActivity extends BaseMvpActivity<IBaseView, HelpPresenter>  {

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        setTitleText("办卡进度");
    }



    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_card_flow;
    }


    @OnClick(R.id.card_flow_search)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        PublicData.getInstance().webviewUrl = "http://www.sh.icbc.com.cn";
        PublicData.getInstance().webviewTitle = "办卡进度查询";
        PublicData.getInstance().isCheckLogin = false;
        Act.getInstance().gotoIntent(this, BrowserActivity.class);
    }


}
