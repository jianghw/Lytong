package com.tzly.ctcyh.cargo.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
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
            registerUser();
            return true;
        }
    }

    private static void registerUser() {
        ServiceRouter.registerComponent(ServiceRouter.USER_LIKE);
    }

    public static boolean isLogin() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isLogin();
        } else {//注册机开始工作
            registerUser();
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
            registerUser();
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
            registerUser();
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
    public static void gotoOilMapActivity(Context context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoOilMapActivity(context);
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
        }
    }

    /**
     * 主页优惠
     */
    public static void gotoMainActivity(Context context, int position) {
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
     * 993加油充值页面
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
     * 加油统一接入口
     */
    public static void gotoOilEnterActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.oil_enter_host,
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
    public static void gotoActiveActivity(Context context, String channel, String date) {
        Bundle bundle = new Bundle();
        bundle.putString(RouterGlobal.putExtra.channel_active, channel);
        if (TextUtils.isEmpty(date)) date = "";
        bundle.putString(RouterGlobal.putExtra.channel_register_date, date);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.active_host,
                bundle);
    }

    /**
     * html
     */
    public static void gotoHtmlActivity(Context context, String title, String url) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoHtmlActivity(context, title, url);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 去办畅通卡
     */
    public static void gotoApplyCardFirstActivity(Context activity) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoApplyCardFirstActivity(activity);
        } else {//注册机开始工作
            registerMain();
        }
    }

    private static Object getMainObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(IMainService.class.getSimpleName());
    }

    private static void registerMain() {
        ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
    }

    /**
     * 加油办卡页面
     */
    public static void gotoBidOilActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.bid_oil_host,
                bundle);
    }

    /**
     * 97 加油
     */
    public static void gotoDiscountOilActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.discount_oil_host,
                bundle);
    }

    /**
     * 97 加油 申请办卡
     */
    public static void gotoFoldOilActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.fold_oil_host,
                bundle);
    }

    /**
     * 驾照查分
     */
    public static void gotoLicenseCargoActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.license_cargo_host,
                bundle);
    }

    /**
     * 驾照查分结果
     */
    public static void gotoLicenseResultActivity(Context context, String score) {
        Bundle bundle = new Bundle();
        bundle.putString(CargoGlobal.putExtra.license_score_extra, score);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.cargo_scheme + "://" + RouterGlobal.Host.license_result_host,
                bundle);
    }

    /**
     * 获取fragment
     */
    public static Fragment getAdvActiveFragment() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
           return service.getAdvActiveFragment();
        } else {//注册机开始工作
            ServiceRouter.registerComponent(ServiceRouter.MAIN_LIKE);
            return new Fragment();
        }
    }
}
