package com.zantong.mobilecttx.user.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.fragment.MsgFragment;

/**
 * 我的消息
 * @author Sandy
 * create at 16/6/6 下午3:31
 */
public class MsgActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements IBaseView{

    private TextView[] mTabTexts;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
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
        setTitleSelText("用户消息", "系统消息");
    }

    @Override
    public void initData() {
        mTabTexts = new TextView[]{getTitleLeft(),getTitleRight()};

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MsgFragment helpFragment = new MsgFragment(mTabTexts);
        transaction.replace(R.id.lay_base_frame, helpFragment);
        transaction.commit();
    }
}
