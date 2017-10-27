package com.tzly.ctcyh.user.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.user.global.UserGlobal;
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
        return new String[]{UserGlobal.Host.login_host};
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (UserGlobal.Host.login_host.equals(host)) {
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
        if (UserGlobal.Host.login_host.equals(host)) return false;

        return loginActivity();
    }

    protected boolean loginActivity() {ServiceRouter serviceRouter = ServiceRouter.getInstance();
        if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
            IUserService service = (IUserService) serviceRouter
                    .getService(IUserService.class.getSimpleName());
            boolean userLogin = service.isUserLogin();
            return !userLogin;
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return true;
        }
    }

    @Override
    protected String verifySchemeToLib() {
        return UserGlobal.Scheme.user_scheme;
    }
}
