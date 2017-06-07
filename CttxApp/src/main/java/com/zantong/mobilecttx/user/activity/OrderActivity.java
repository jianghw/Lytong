package com.zantong.mobilecttx.user.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.presenter.OrderPresenter;
import com.zantong.mobilecttx.user.fragment.OrderFragment;
import com.zantong.mobilecttx.interf.IOrderView;

/**
 * 我的订单
 * @author Sandy
 * create at 16/6/6 上午11:35
 */
public class OrderActivity extends BaseMvpActivity<IOrderView,OrderPresenter> implements View.OnClickListener, IOrderView {

    @Override
    public void initView() {
        setTitleText("我的订单");
    }

    @Override
    public void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        OrderFragment orderFragment = new OrderFragment();
        transaction.replace(R.id.mine_order_layout, orderFragment);
        transaction.commit();
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_order_activity;
    }

    @Override
    public void onClick(View v) {
//        presenter.login(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String getOrderType() {
        return null;
    }


}
