package com.zantong.mobilecttx.user.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.user.fragment.MineFragment;

/**
 * 个人中心
 * @author Sandy
 * create at 16/6/6 下午3:31
 */
public class MineActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements IBaseView{


    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_msg_activity;
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
        setTitleText("个人中心");
        setEnsureImg(R.mipmap.icon_set);
    }

    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        MobclickAgent.onEvent(this, Config.getUMengID(26));
    }

    @Override
    public void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MineFragment helpFragment = new MineFragment();
        transaction.replace(R.id.mine_msg_layout, helpFragment);
        transaction.commit();
    }
}
