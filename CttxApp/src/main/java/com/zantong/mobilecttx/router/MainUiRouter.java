package com.zantong.mobilecttx.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.service.IUserService;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.guide_v.GuideCTActivity;
import com.zantong.mobilecttx.home_v.HomeMainActivity;

/**
 * 向外提供的路由规则
 */

public class MainUiRouter extends LibUiRouter implements IComponentRouter {

    /**
     * 单例
     */
    public static MainUiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MainUiRouter INSTANCE = new MainUiRouter();
    }

    @Override
    protected String[] initHostToLib() {
        return new String[]{
                MainGlobal.Host.home_host,
                MainGlobal.Host.guide_host
        };
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (MainGlobal.Host.home_host.equals(host)) {
            intent.setClass(context, HomeMainActivity.class);
        } else if (MainGlobal.Host.guide_host.equals(host)) {
            intent.setClass(context, GuideCTActivity.class);
        } else {
            return true;
        }
        return false;
    }


    /**
     * 不用登录逻辑
     */
    @Override
    protected boolean excludeLoginActivity(String host) {
        //可添加不需要登录业务
        return !MainGlobal.Host.guide_host.equals(host)
                && !MainGlobal.Host.home_host.equals(host)
                && loginActivity();
    }

    protected boolean loginActivity() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
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
        return MainGlobal.Scheme.main_scheme;
    }
}
