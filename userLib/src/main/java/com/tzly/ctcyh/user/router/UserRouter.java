package com.tzly.ctcyh.user.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.IMainService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.tzly.ctcyh.user.global.UserGlobal;

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

    public static void gotoLoginActivity(Context context, String phone, String pw) {
        Bundle bundle = new Bundle();
        bundle.putString(UserGlobal.putExtra.user_login_phone, phone);
        bundle.putString(UserGlobal.putExtra.user_login_pw, pw);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.login_host,
                bundle);
    }

    /**
     * 发验证码
     */
    public static void gotoCodeActivity(Context context, String host) {
        Bundle bundle = new Bundle();
        bundle.putString(UserGlobal.Host.login_code_host, host);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.send_code_host,
                bundle);
    }

    /**
     * 注册页面
     */
    public static void gotoRegisterActivity(Context context, String host, String phone) {
        Bundle bundle = new Bundle();
        bundle.putString(UserGlobal.Host.send_register_host, host);
        bundle.putString(UserGlobal.putExtra.user_login_phone, phone);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.register_host,
                bundle);
    }

    private static Object getUserObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(IUserService.class.getSimpleName());
    }

    /**
     * 页面跳转判断是否登录用
     * true-->跳出登录页面
     */
    public static boolean gotoByIsLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isUserByLogin();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.USER_LIKE);
            return true;
        }
    }

    public static String getDeviceId() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getPhoneDeviceId();
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.USER_LIKE);
            return "0123456789";
        }
    }

    /**
     * 主模块业务
     */
    private static Object getMainObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(IMainService.class.getSimpleName());
    }

    /**
     * 去绑定畅通卡页面
     */
    public static void loginFilenumDialog(FragmentActivity activity) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.loginFilenumDialog(activity);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
        }
    }

    /**
     * 获取设备推动id
     */
    public static String getPushId() {
        Object object = getMainObject();
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
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoMainActivity(activity, i);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }
}
