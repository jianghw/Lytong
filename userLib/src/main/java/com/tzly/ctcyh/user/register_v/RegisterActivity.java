package com.tzly.ctcyh.user.register_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.KeyboardUtils;
import com.tzly.ctcyh.user.R;
import com.tzly.ctcyh.user.global.UserGlobal;

/**
 * 注册界面
 */
public class RegisterActivity extends AbstractBaseActivity {

    private RegisterFragment mFragment;
    private String mExtraHost;
    private String mExtraPhone;

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        finish();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(UserGlobal.Host.send_register_host))
                    mExtraHost = bundle.getString(UserGlobal.Host.send_register_host);
                if (intent.hasExtra(UserGlobal.putExtra.user_login_phone))
                    mExtraPhone = bundle.getString(UserGlobal.putExtra.user_login_phone);
            }
        }
    }

    @Override
    protected void bindFragment() {
        if (mExtraHost.equals(UserGlobal.Host.code_pw_host))
            titleContent("重置密码");
        else if (mExtraHost.equals(UserGlobal.Host.code_register_host))
            titleContent("注   册");
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = RegisterFragment.newInstance(mExtraHost,mExtraPhone);
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onPause() {
        super.onPause();

        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mFragment = null;
    }
}