package com.zantong.mobile.msg_v;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tzly.annual.base.util.ui.FragmentUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.presenter.MegTypeAtyPresenter;

/**
 * 消息页面
 */

public class MegTypeActivity extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {}

    @Override
    protected int getContentResId() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("消息");

        MegTypeFragment megTypeFragment = MegTypeFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentUtils.addFragment(
                fragmentManager, megTypeFragment, R.id.lay_base_frame, false, true);


        MegTypeAtyPresenter mPresenter = new MegTypeAtyPresenter(
                Injection.provideRepository(getApplicationContext()), megTypeFragment);
    }

    @Override
    protected void DestroyViewAndThing() {
    }

}
