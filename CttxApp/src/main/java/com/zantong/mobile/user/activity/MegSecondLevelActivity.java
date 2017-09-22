package com.zantong.mobile.user.activity;

import android.content.Intent;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.MvpBaseActivity;
import com.zantong.mobile.common.Injection;
import com.zantong.mobile.presenter.MegSecondLevelAtyPresenter;
import com.zantong.mobile.user.fragment.MegSecondLevelFragment;

import com.tzly.annual.base.util.AtyUtils;

import static com.zantong.mobile.user.fragment.MegTypeFragment.MESSAGE_RESULT_CODE;

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
            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), megSecondLevelFragment, R.id.lay_base_frame);
        }

        MegSecondLevelAtyPresenter mPresenter = new MegSecondLevelAtyPresenter(
                Injection.provideRepository(getApplicationContext()), megSecondLevelFragment);
    }

    public void setResultForRefresh() {
        setResult(MESSAGE_RESULT_CODE);
    }
}
