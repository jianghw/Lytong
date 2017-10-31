package com.zantong.mobilecttx.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.daijia.activity.DrivingActivity;
import com.zantong.mobilecttx.fahrschule.activity.SubjectActivity;
import com.zantong.mobilecttx.guide_v.GuideCTActivity;
import com.zantong.mobilecttx.home.activity.CaptureActivity;
import com.zantong.mobilecttx.home_v.HomeMainActivity;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;

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
                RouterGlobal.Host.home_host,
                RouterGlobal.Host.guide_host,
                RouterGlobal.Host.map_host,
                RouterGlobal.Host.driving_host,
                RouterGlobal.Host.meg_type_host,
                RouterGlobal.Host.capture_host,
                RouterGlobal.Host.recharge_host,
                RouterGlobal.Host.subject_host,
                RouterGlobal.Host.in_exist_host
        };
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (RouterGlobal.Host.home_host.equals(host)) {
            intent.setClass(context, HomeMainActivity.class);
        } else if (RouterGlobal.Host.guide_host.equals(host)) {
            intent.setClass(context, GuideCTActivity.class);
        } else if (RouterGlobal.Host.map_host.equals(host)) {
            intent.setClass(context, BaiduMapParentActivity.class);
        } else if (RouterGlobal.Host.driving_host.equals(host)) {
            intent.setClass(context, DrivingActivity.class);
        } else if (RouterGlobal.Host.meg_type_host.equals(host)) {
            intent.setClass(context, MegTypeActivity.class);
        } else if (RouterGlobal.Host.capture_host.equals(host)) {
            intent.setClass(context, CaptureActivity.class);
        } else if (RouterGlobal.Host.recharge_host.equals(host)) {
            intent.setClass(context, RechargeActivity.class);
        } else if (RouterGlobal.Host.subject_host.equals(host)) {
            intent.setClass(context, SubjectActivity.class);
        } else {
            initLoginBean();
            return true;
        }
        return false;
    }

    protected void initLoginBean() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
            IUserService service = (IUserService) serviceRouter
                    .getService(IUserService.class.getSimpleName());
            service.initLoginData();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    /**
     * 不用登录逻辑
     */
    @Override
    protected boolean excludeLoginActivity(String host) {
        //可添加不需要登录业务
        return !RouterGlobal.Host.guide_host.equals(host)
                && !RouterGlobal.Host.home_host.equals(host)
                && !RouterGlobal.Host.capture_host.equals(host)
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
        return RouterGlobal.Scheme.main_scheme;
    }
}
