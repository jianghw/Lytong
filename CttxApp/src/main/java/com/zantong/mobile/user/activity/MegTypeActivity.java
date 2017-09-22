package com.zantong.mobile.user.activity;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.MvpBaseActivity;
import com.zantong.mobile.common.Injection;
import com.zantong.mobile.presenter.MegTypeAtyPresenter;
import com.zantong.mobile.user.fragment.MegTypeFragment;

import com.tzly.annual.base.global.JxConfig;
import com.tzly.annual.base.util.AtyUtils;

/**
 * 消息页面
 * Created by zhoujie on 2017/2/13.
 */

public class MegTypeActivity extends MvpBaseActivity {

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void setTitleView() {
        setTitleText("消息");
    }

    @Override
    protected void initMvPresenter() {
        MegTypeFragment megTypeFragment =
                (MegTypeFragment) getSupportFragmentManager().findFragmentById(R.id.lay_base_frame);
        if (megTypeFragment == null) {
            megTypeFragment = MegTypeFragment.newInstance();
            AtyUtils.addFragmentToActivity(
                    getSupportFragmentManager(), megTypeFragment, R.id.lay_base_frame);
        }

        MegTypeAtyPresenter mPresenter = new MegTypeAtyPresenter(
                Injection.provideRepository(getApplicationContext()), megTypeFragment);

        JxConfig.getInstance().eventIdByUMeng(16);
    }
}
