package com.zantong.mobilecttx.car.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.SetPayCarPresenter;
import com.zantong.mobilecttx.car.fragment.SetPayCarFragment;

public class SetPayCarActivity extends BaseMvpActivity<IBaseView, SetPayCarPresenter> implements IBaseView {

    @Override
    public SetPayCarPresenter initPresenter() {
        return new SetPayCarPresenter(SetPayCarActivity.this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_set_paycar;
    }

    @Override
    public void initView() {
        setTitleText("更改已绑定车辆");
    }

    @Override
    public void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SetPayCarFragment mFragment = new SetPayCarFragment();
        transaction.replace(R.id.activity_set_paycar_layout, mFragment);
        transaction.commit();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
