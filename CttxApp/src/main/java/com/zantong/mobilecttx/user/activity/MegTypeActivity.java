package com.zantong.mobilecttx.user.activity;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.presenter.MegTypeAtyPresenter;
import com.zantong.mobilecttx.user.fragment.MegTypeFragment;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.util.AtyUtils;

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

        GlobalConfig.getInstance().eventIdByUMeng(16);
    }
}
