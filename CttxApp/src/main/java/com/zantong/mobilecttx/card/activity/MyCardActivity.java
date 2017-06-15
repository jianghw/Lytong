package com.zantong.mobilecttx.card.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.fragment.MyCardFragment;
import com.zantong.mobilecttx.presenter.HelpPresenter;

public class MyCardActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {


    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_help_activity;
    }

    @Override
    public void initView() {
        setTitleText("我的畅通卡");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initContent();
    }

    public void initContent() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MyCardFragment mBindCardFragment = new MyCardFragment();
        transaction.replace(R.id.mine_help_layout, mBindCardFragment);
        transaction.commit();
    }

    @Override
    public void initData() {

    }
}
