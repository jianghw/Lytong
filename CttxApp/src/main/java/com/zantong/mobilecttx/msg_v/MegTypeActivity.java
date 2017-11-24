package com.zantong.mobilecttx.msg_v;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.MobUtils;
import com.zantong.mobilecttx.R;

/**
 * 消息页面
 * Created by zhoujie on 2017/2/13.
 */

public class MegTypeActivity extends AbstractBaseActivity {

    private MegTypeFragment megTypeFragment;

    @Override
    protected void bundleIntent(Intent intent) {}

    @Override
    protected void newIntent(Intent intent) {}

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bindFragment() {
        titleContent("我的消息");
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (megTypeFragment == null) {
            megTypeFragment = MegTypeFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, megTypeFragment, R.id.lay_base_frame);
        MobUtils.getInstance().eventIdByUMeng(16);
    }
}
