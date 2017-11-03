package com.zantong.mobilecttx.user.activity;

import android.content.Intent;

import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.presenter.MegSecondLevelAtyPresenter;
import com.zantong.mobilecttx.user.fragment.MegSecondLevelFragment;

/**
 * 消息二级页面
 */

public class MegSecondLevelActivity extends MvpBaseActivity {

    /**
     * 上页面传递过来参数
     */
    private String title = "数据出错";
    private String id;

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void setTitleView() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            id = intent.getStringExtra("id");
        }
        setTitleText(title);
    }

    @Override
    protected void initMvPresenter() {
        MegSecondLevelFragment megSecondLevelFragment =
                (MegSecondLevelFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
        if (megSecondLevelFragment == null) {
            megSecondLevelFragment = MegSecondLevelFragment.newInstance(id, title);
            FragmentUtils.add(getSupportFragmentManager(), megSecondLevelFragment, R.id.lay_base_frame, false, true);
        }

        MegSecondLevelAtyPresenter mPresenter = new MegSecondLevelAtyPresenter(
                Injection.provideRepository(getApplicationContext()), megSecondLevelFragment);
    }

}
