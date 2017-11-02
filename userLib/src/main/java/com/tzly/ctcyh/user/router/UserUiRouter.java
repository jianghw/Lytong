package com.tzly.ctcyh.user.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.service.RouterGlobal;
import com.tzly.ctcyh.user.login_v.LoginActivity;

/**
 * 向外提供的路由规则
 */

public class UserUiRouter extends LibUiRouter implements IComponentRouter {

    /**
     * 单例
     */
    public static UserUiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final UserUiRouter INSTANCE = new UserUiRouter();
    }

    @Override
    protected String[] initHostToLib() {
        return new String[]{RouterGlobal.Host.login_host};
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (RouterGlobal.Host.login_host.equals(host)) {
            intent.setClass(context, LoginActivity.class);
        } else {
            return true;
        }
        return false;
    }

    /**
     * 登录逻辑
     */
    @Override
    protected boolean excludeLoginActivity(String host) {
        //不需要登录
        return !RouterGlobal.Host.login_host.equals(host) && gotoByIsLogin();
    }

    private boolean gotoByIsLogin() {
        return UserRouter.gotoByIsLogin();
    }

    @Override
    protected String verifySchemeToLib() {
        return RouterGlobal.Scheme.user_scheme;
    }
}
