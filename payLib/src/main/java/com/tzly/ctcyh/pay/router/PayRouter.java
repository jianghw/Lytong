package com.tzly.ctcyh.pay.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tzly.ctcyh.pay.BuildConfig;
import com.tzly.ctcyh.pay.global.PayGlobal;
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

    /**
     * 选优惠页面
     */
    public static void gotoCouponListActivity(Activity context, String type, int payType) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.coupon_list_type, type);
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, payType);
        bundle.putString(PayGlobal.Host.coupon_list_host, RouterGlobal.Host.coupon_list_host);

        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.coupon_list_host,
                bundle, PayGlobal.requestCode.coupon_list_choice);
    }

    /**
     * 支付宝 页面html
     */
    public static void gotoAliHtmlActivity(Activity context, String title,
                                           String extraOrderId, int payType, int price, int couponBeanId) {
        String url = BuildConfig.App_Url
                ? "http://dev.liyingtong.com/" : "http://biz.liyingtong.com/";
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("aliPay/aliPayHtml");
        sb.append("?orderId=").append(extraOrderId);
        sb.append("&amount=").append(price);
        if (couponBeanId > 0) sb.append("&couponUserId=").append(couponBeanId);

        gotoHtmlActivity(context, title, sb.toString(), extraOrderId, payType);
    }

    /**
     * 支付
     */
    public static void gotoHtmlActivity(Activity context, String title,
                                        String url, String extraOrderId, int payType) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, title);
        bundle.putString(PayGlobal.putExtra.web_url_extra, url);
        bundle.putString(PayGlobal.putExtra.web_orderId_extra, extraOrderId);
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, payType);

        gotoHtmlActivity(context, bundle);
    }

    /**
     * html
     */
    public static void gotoHtmlActivity(Activity context, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, title);
        bundle.putString(PayGlobal.putExtra.web_url_extra, url);

        gotoHtmlActivity(context, bundle);
    }

    private static void gotoHtmlActivity(Activity context, Bundle bundle) {
        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.html_5_host,
                bundle, PayGlobal.requestCode.pay_type_price);
    }

    /**
     * 支付页面页面
     */
    public static void gotoPayTypeActivity(Activity context, String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.pay_type_order, orderId);
        bundle.putString(PayGlobal.Host.pay_type_host, PayGlobal.Host.pay_type_host);

        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.pay_type_host,
                bundle, PayGlobal.requestCode.pay_type_choice);
    }

    /**
     * 订单详情
     */
    public static void gotoOrderDetailActivity(Activity context, String orderId, int couponType) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null) {
            IMainService service = (IMainService) object;
            service.gotoOrderDetailActivity(context, orderId, couponType);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 订单完成状态页面
     */
    public static void gotoOrderSucceedActivity(Activity context, String orderId, int couponType) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null) {
            IMainService service = (IMainService) object;
            service.gotoOrderSucceedActivity(context, orderId, couponType);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 用户id
     */
    public static String getUserID() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserID();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getRASUserID() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getRASUserID();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    /**
     * 用户驾档编号
     */
    public static String getUserFilenum() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserFilenum();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    /**
     * 手机号码
     */
    public static String getUserPhoenum() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserPhoenum();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    /**
     * 只初始化数据
     */
    public static boolean isLogin() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.isLogin();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return false;
        }
    }

    /**
     * 绑定畅通卡页面
     */
    public static void gotoUnblockedCardActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null) {
            IMainService service = (IMainService) object;
            service.gotoUnblockedCardActivity(context);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    public static void gotoMyCardActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null) {
            IMainService service = (IMainService) object;
            service.gotoMyCardActivity(context);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 违章列表夜间
     */
    public static void gotoViolationListActivity(Activity context, String carnum, String enginenum, String carnumtype) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null) {
            IMainService service = (IMainService) object;
            service.gotoViolationListActivity(context, carnum, enginenum, carnumtype);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }

    /**
     * 拍照页面
     */
    public static void gotoOcrCameraActivity(Activity context) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IMainService.class.getSimpleName());
        if (object != null) {
            IMainService service = (IMainService) object;
            service.gotoOcrCameraActivity(context);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.zantong.mobilecttx.like.MainAppLike");
        }
    }
}
