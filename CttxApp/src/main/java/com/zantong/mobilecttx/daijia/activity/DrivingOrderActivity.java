package com.zantong.mobilecttx.daijia.activity;

import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.daijia.fragment.DrivingFragment;

/**
 * 代驾订单
 * Created by zhoujie on 2017/2/17.
 */

public class DrivingOrderActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Override
    public void initView() {
        setTitleText("代驾订单");
    }

    @Override
    public void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DrivingFragment orderFragment = new DrivingFragment();
        transaction.replace(R.id.mine_order_layout, orderFragment);
        transaction.commit();
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_order_activity ;
    }
}
