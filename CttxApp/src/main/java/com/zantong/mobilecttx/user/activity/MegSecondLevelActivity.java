package com.zantong.mobilecttx.user.activity;

import android.content.Intent;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.presenter.MegSecondLevelAtyPresenter;
import com.zantong.mobilecttx.user.fragment.MegSecondLevelFragment;

import cn.qqtheme.framework.util.AtyUtils;

import static com.zantong.mobilecttx.user.fragment.MegTypeFragment.MESSAGE_RESULT_CODE;

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
        return R.layout.mine_msg_activity;
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
                (MegSecondLevelFragment) getSupportFragmentManager().findFragmentById(R.id.mine_msg_layout);
        if (megSecondLevelFragment == null) {
            megSecondLevelFragment = MegSecondLevelFragment.newInstance(id, title);
            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), megSecondLevelFragment, R.id.mine_msg_layout);
        }

        MegSecondLevelAtyPresenter mPresenter = new MegSecondLevelAtyPresenter(
                Injection.provideRepository(getApplicationContext()), megSecondLevelFragment);
    }

    public void setResultForRefresh() {
        setResult(MESSAGE_RESULT_CODE);
    }
}
