package com.zantong.mobilecttx.user.activity;

import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

import butterknife.OnClick;

/**
 * 选择城市
 * @author Sandy
 * create at 16/9/12 上午11:13
 */
public class AddrActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements IBaseView{

     @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_addr;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void initView() {
        setTitleText("选择城市");
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.activity_addr_item)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.activity_addr_item:
                finish();
                break;
        }
    }
}
