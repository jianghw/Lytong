package com.zantong.mobilecttx.card.activity;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

public class CardNoticeActivity extends BaseMvpActivity<IBaseView, HelpPresenter>  {

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        setTitleText("办卡须知");
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_card_notice;
    }


}
