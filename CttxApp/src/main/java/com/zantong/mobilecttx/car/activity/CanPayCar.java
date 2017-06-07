package com.zantong.mobilecttx.car.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.CanPayCarAPresenter;
import com.zantong.mobilecttx.car.fragment.CanPayCarFragment;

public class CanPayCar extends BaseMvpActivity<IBaseView,CanPayCarAPresenter> implements View.OnClickListener, IBaseView{

    @Override
    public CanPayCarAPresenter initPresenter() {
        return new CanPayCarAPresenter(CanPayCar.this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_help_activity;
    }

    @Override
    public void initView() {
        setTitleText("可缴费车辆");
    }

    @Override
    public void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        CanPayCarFragment mCanPayCarFragment = new CanPayCarFragment();
        transaction.replace(R.id.mine_help_layout, mCanPayCarFragment);
        transaction.commit();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
