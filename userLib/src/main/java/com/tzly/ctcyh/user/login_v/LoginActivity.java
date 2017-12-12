package com.tzly.ctcyh.user.login_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.KeyboardUtils;
import com.tzly.ctcyh.user.R;
import com.tzly.ctcyh.user.global.UserGlobal;
import com.tzly.ctcyh.user.router.UserRouter;

/**
 * 登陆界面
 */
public class LoginActivity extends AbstractBaseActivity {

    private LoginFragment mFragment;

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        finish();
    }

    /**
     * 右边文字点击 注册页码
     */
    protected void rightClickListener() {
        UserRouter.gotoRegisterActivity(this);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_base_frame;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            String mExtraPhone = null;
            String mExtraPassword = null;

            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(UserGlobal.putExtra.user_login_phone))
                mExtraPhone = bundle.getString(UserGlobal.putExtra.user_login_phone);
            if (intent.hasExtra(UserGlobal.putExtra.user_login_pw))
                mExtraPassword = bundle.getString(UserGlobal.putExtra.user_login_pw);

            if (mFragment != null &&
                    !TextUtils.isEmpty(mExtraPhone) && !TextUtils.isEmpty(mExtraPassword))
                mFragment.automaticOperation(mExtraPhone, mExtraPassword);
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("欢迎加入畅通车友会");
        titleMore("注册");
    }

    @Override
    protected void initContentData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //默认页面显示
        if (mFragment == null) {
            mFragment = LoginFragment.newInstance();
        }
        FragmentUtils.add(fragmentManager, mFragment, R.id.lay_base_frame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        KeyboardUtils.hideSoftInput(this);
        mFragment = null;
    }
}
