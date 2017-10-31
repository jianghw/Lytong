package com.tzly.ctcyh.pay.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tzly.ctcyh.pay.BuildConfig;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.UiRouter;
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
    public static void gotoCouponListActivity(Activity context, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.coupon_list_type, type);
        bundle.putString(PayGlobal.Host.coupon_list_host, RouterGlobal.Host.coupon_list_host);

        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.coupon_list_host,
                bundle, PayGlobal.requestCode.coupon_list_choice);
    }

    /**
     * 支付页面html
     */
    public static void gotoHtmlActivity(Activity context,
                                        String title, String extraOrderId, int payType, int price, int couponBeanId) {
        String url = BuildConfig.App_Url
                ? "http://dev.liyingtong.com/" : "http://biz.liyingtong.com/";
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("aliPay/aliPayHtml");
        sb.append("?orderId=").append(extraOrderId);
        sb.append("&amount=").append(price);
        if (couponBeanId != 0) sb.append("&couponUserId=").append(couponBeanId);

        gotoHtmlActivity(context, title, sb.toString(), extraOrderId, payType);
    }

    public static void gotoHtmlActivity(Activity context,
                                         String title, String url, String extraOrderId, int payType) {
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_title_extra, title);
        bundle.putString(PayGlobal.putExtra.web_url_extra, url);
        bundle.putString(PayGlobal.putExtra.web_orderId_extra, extraOrderId);
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, payType);

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
        bundle.putString(PayGlobal.Host.pay_type_host,PayGlobal.Host.pay_type_host);

        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.pay_scheme + "://" + RouterGlobal.Host.pay_type_host,
                bundle, PayGlobal.requestCode.pay_type_choice);
    }
}
