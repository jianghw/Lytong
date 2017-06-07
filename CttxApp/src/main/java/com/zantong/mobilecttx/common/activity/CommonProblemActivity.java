package com.zantong.mobilecttx.common.activity;

import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.common.fragment.CommonProblemFragment;

/**
 * 常见问题
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {
    @Override
    public void initView() {
        setTitleText("常见问题");
    }

    @Override
    public void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CommonProblemFragment orderFragment = new CommonProblemFragment();
        transaction.replace(R.id.mine_order_layout, orderFragment);
        transaction.commit();
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_order_activity;
    }
}
