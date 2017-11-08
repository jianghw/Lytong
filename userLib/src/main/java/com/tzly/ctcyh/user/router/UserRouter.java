package com.tzly.ctcyh.user.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.IMainService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;

/**
 * Created by jianghw on 2017/10/31.
 * Description:
 * Update by:
 * Update day:
 */

public final class UserRouter {
    /**
     * 登录页面
     */
    public static void gotoLoginActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.login_host,
                bundle);
    }

    /**
     * 页面跳转判断是否登录用
     * true-->跳出登录页面
     */
    public static boolean gotoByIsLogin() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            boolean userLogin = service.isUserByLogin();
            return !userLogin;
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return true;
        }
    }

    /**
     * 去绑定畅通卡页面
     */
    public static void loginFilenumDialog(Activity activity) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.loginFilenumDialog(activity);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 注册页面
     */
    public static void gotoRegisterActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoRegisterActivity(context);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 密码页面
     */
    public static void gotoResetActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoResetActivity(context);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 获取设备推动id
     */
    public static String getPushId() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            return service.getPushId();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
            return "";
        }
    }

    public static void gotoMainActivity(Activity activity, int i) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoMainActivity(activity, i);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }
}
