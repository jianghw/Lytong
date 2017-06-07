package com.zantong.mobilecttx.daijia.activity;

import android.content.Intent;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

/**
 * 代驾
 * @author Sandy
 * create at 16/12/26 下午10:15
 */
public class DaiJiaActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements IBaseView{

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.home_daijia_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        int res = intent.getIntExtra("res",0);
        if (res == 0){
            setTitleText("代驾");
        }else{
            setTitleText("油卡充值");
        }
    }

    @Override
    public void initData() {
    }
}
