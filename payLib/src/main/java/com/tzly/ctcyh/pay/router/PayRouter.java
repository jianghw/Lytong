package com.tzly.ctcyh.pay.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.ICargoService;
import com.tzly.ctcyh.service.IMainService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;

/**
 * Created by jianghw on 2017/10/31.
 * Description:
 * Update by:
 * Update day:
 */

public final class PayRouter {
    /**
     * 登录页面
     */
    public static void gotoLoginActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.login_host,
                bundle);
    }

    private static Object getUserObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(IUserService.class.getSimpleName());
    }

    /**
     * 只初始化数据
     */
    public static boolean isLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isLogin();
        } else {//注册机开始工作
            registerUser();
            return false;
        }
    }

    private static void registerUser() {
        ServiceRouter.registerComponent(ServiceRouter.USER_LIKE);
    }

    /**
     * 页面跳转判断是否登录用
     */
    public static boolean gotoByIsLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isUserByLogin();
        } else {//注册机开始工作
            registerUser();
            return true;
        }
    }

    /**
     * 用户id
     */
    public static String getUserID() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserID();
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    public static String getRASUserID() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getRASUserID();
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    /**
     * 加密
     */
    public static String getRASByStr(String str) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getRASByStr(str);
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    /**
     * 用户驾档编号
     */
    public static String getUserFilenum() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserFilenum();
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    /**
     * 手机号码
     */
    public static String getUserPhoenum() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserPhoenum();
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    /**
     * 设备号码
     */
    public static String getDeviceId() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getPhoneDeviceId();
        } else {//注册机开始工作
            registerUser();
            return "0123456789";
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
     * 订单详情
     */
    public static void gotoOrderDetailActivity(Activity context, String orderId, int couponType) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoOrderDetailActivity(context, orderId, couponType);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 订单完成状态页面
     */
    public static void gotoOrderSucceedActivity(Activity context, String orderId, int couponType) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoOrderSucceedActivity(context, orderId, couponType);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 绑定畅通卡页面
     */
    public static void gotoUnblockedCardActivity(Activity context) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoUnblockedCardActivity(context);
        } else {//注册机开始工作
            registerMain();
        }
    }

    public static void gotoMyCardActivity(Activity context) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoMyCardActivity(context);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 违章列表夜间
     */
    public static void gotoViolationListActivity(Context context, String carnum, String enginenum, String carnumtype) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoViolationListActivity(context, carnum, enginenum, carnumtype);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 去往活动规则页面
     */
    public static void gotoActiveActivity(Context context, int i) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoActiveActivity(context, i);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 去往主页
     */
    public static void gotoMainActivity(Context context, int i) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoMainActivity(context, i);
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

    public static void gotoHtmlActivity(Context activity, String title, String msg) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoHtmlActivity(activity, title, msg);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 拍照页面
     */
    public static void gotoOcrCameraActivity(Activity context) {
        Object object = getMainObject();
        if (object != null && object instanceof IMainService) {
            IMainService service = (IMainService) object;
            service.gotoOcrCameraActivity(context);
        } else {//注册机开始工作
            registerMain();
        }
    }

    /**
     * 选优惠页面
     */
    public static void gotoCouponListActivity(Activity context, int type, int payType) {
        Bundle bundle = new Bundle();
        bundle.putInt(PayGlobal.putExtra.coupon_list_type, type);
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, payType);
        bundle.putString(PayGlobal.Host.coupon_list_host, RouterGlobal.Host.coupon_list_host);

        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.coupon_list_host,
                bundle, PayGlobal.requestCode.coupon_list_choice);
    }


    /**
     * 支付方式页面
     */
    public static void gotoPayTypeActivity(Activity context, String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.pay_type_order, orderId);
        bundle.putString(PayGlobal.Host.pay_type_host, PayGlobal.Host.pay_type_host);

        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.pay_type_host,
                bundle);
    }

    /**
     * 优惠券列表
     */
    public static void gotoCouponStatusActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.coupon_status_host,
                bundle);
    }

    /**
     * 优惠劵详情
     */
    public static void gotoCouponDetailActivity(Activity activity, String couponId) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.coupon_detail_id, couponId);

        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.coupon_detail_host,
                bundle);
    }

    /**
     * 支付成功页面
     */
    public static void gotoPaySucceedActivity(Context context, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_goods_type_extra, type);

        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.pay_succeed_host,
                bundle);
    }

    /**
     * web_html
     */
    public static void gotoWebHtmlActivity(Context context, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, title);
        bundle.putString(PayGlobal.putExtra.web_url_extra, url);

        gotoWebHtmlActivity(context, bundle);
    }

    /**
     * 渠道支付
     */
    public static void gotoWebHtmlActivity(Context context, String title, String url,
                                           String orderId, int payType, String channel) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, title);
        bundle.putString(PayGlobal.putExtra.web_url_extra, url);

        if (!TextUtils.isEmpty(orderId))
            bundle.putString(PayGlobal.putExtra.web_orderId_extra, orderId);
        if (payType > 0)
            bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, payType);
        if (!TextUtils.isEmpty(channel))
            bundle.putString(PayGlobal.putExtra.web_pay_channel_extra, channel);

        gotoWebHtmlActivity(context, bundle);
    }

    /**
     * 支付宝 页面html
     */
    public static void gotoAliHtmlActivity(Context context, String title, String extraOrderId,
                                           int payType, int price, int couponBeanId, String channel) {
        String url = BuildConfig.isDeta ? BuildConfig.beta_base_url : BuildConfig.release_base_url;

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("aliPay/aliPayHtml");
        sb.append("?orderId=").append(extraOrderId);
        sb.append("&amount=").append(price);
        if (couponBeanId > 0) sb.append("&couponUserId=").append(couponBeanId);

        gotoWebHtmlActivity(context, title, sb.toString(), extraOrderId, payType, channel);
    }

    /**
     * 违章
     */
    public static void gotoWebHtmlActivity(Context context, String title, String url,
                                           String num, String enginenum) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, title);
        bundle.putString(PayGlobal.putExtra.web_url_extra, url);

        bundle.putString(PayGlobal.putExtra.web_violationnum_extra, num);
        bundle.putString(PayGlobal.putExtra.web_enginenum_extra, enginenum);

        gotoWebHtmlActivity(context, bundle);
    }

    public static void gotoWebHtmlActivity(Context context, Bundle bundle) {
        if (context instanceof Activity) {
            gotoWebHtmlActivity((Activity) context, bundle);
        } else {
            UiRouter.getInstance().openUriBundle(context,
                    RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.web_html_host,
                    bundle);
        }
    }

    /**
     * 有回调 single失效
     */
    public static void gotoWebHtmlActivity(Activity activity, Bundle bundle) {
        UiRouter.getInstance().openUriForResult(activity,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.web_html_host,
                bundle, PayGlobal.requestCode.pay_html_price);
    }

    /**
     * 行驶证证扫描 110
     */
    public static void gotoVehicleCameraActivity(Activity activity) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoVehicleCameraActivity(activity);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    private static Object getCargoObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(ICargoService.class.getSimpleName());
    }

    private static void registerCargo() {
        ServiceRouter.registerComponent(ServiceRouter.CARGO_LIKE);
    }
}
