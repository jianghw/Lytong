package com.zantong.mobilecttx.user.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.fragment.HelpFragment;

/**
 * 帮助与反馈
 */
public class HelpActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements View.OnClickListener, IBaseView{

    private FragmentManager fragmentManager;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_help_activity;
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
        setTitleText("帮助与反馈");
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        HelpFragment helpFragment = new HelpFragment();
        transaction.replace(R.id.mine_help_layout, helpFragment);
        transaction.commit();
    }
}
