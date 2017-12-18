package com.tzly.ctcyh.cargo.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.ICargoService;
import com.tzly.ctcyh.service.IMainService;
import com.tzly.ctcyh.service.IPayService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;

/**
 * Created by jianghw on 2017/10/31.
 * Description:
 * Update by:
 * Update day:
 */

public final class CargoRouter {

    /**
     * 页面跳转判断是否登录用
     */
    public static boolean gotoByIsLogin() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isUserByLogin();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return true;
        }
    }

    /**
     * 用户id
     */
    public static String getUserID() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserID();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getDeviceId() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getPhoneDeviceId();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "0123456789";
        }
    }

    /**
     * 支付页面
     */
    public static void gotoPayTypeActivity(Activity context, String orderId) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IPayService.class.getSimpleName());
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoPayTypeActivity(context, orderId);
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.PAY_LIKE);
        }
    }

    /**
     * 问题页面
     */
    public static void gotoProblemFeedbackActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoProblemFeedbackActivity(context);
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
        }
    }

    /**
     * 加油地图
     */
    public static void gotoBaiduMapParentActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoBaiduMapParentActivity(context);
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
        }
    }

    /**
     * 主页优惠
     */
    public static void gotoMainActivity(Activity context, int position) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoMainActivity(context, position);
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
        }
    }

    /**
     * 加油充值页面
     */
    public static void gotoRechargeActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.recharge_host,
                bundle);
    }

    /**
     * 加油协议页面
     */
    public static void gotoRechargeAgreementActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.recharge_agree_host,
                bundle);
    }

    /**
     * 驾驶证扫描 110
     */
    public static void gotoDrivingCameraActivity(Activity context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.driving_camera_host,
                bundle, 110);
    }

    /**
     * 行驶证证扫描 110
     */
    public static void gotoVehicleCameraActivity(Activity context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.vehicle_camera_host,
                bundle, 110);
    }

    /**
     * 活动页面逻辑
     */
    public static void gotoActiveActivity(Context context, String channel) {
        Bundle bundle = new Bundle();
        bundle.putString(RouterGlobal.putExtra.channel_active, channel);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.active_host,
                bundle);
    }

    public static void gotoActiveActivity(Context context, String channel,String date) {
        Bundle bundle = new Bundle();
        bundle.putString(RouterGlobal.putExtra.channel_active, channel);
        bundle.putString(RouterGlobal.putExtra.channel_register_date, date);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.active_host,
                bundle);
    }
}
