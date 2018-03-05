package com.tzly.ctcyh.pay.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.pay.coupon_v.CouponDetailActivity;
import com.tzly.ctcyh.pay.coupon_v.CouponListActivity;
import com.tzly.ctcyh.pay.coupon_v.CouponStatusActivity;
import com.tzly.ctcyh.pay.html_v.WebHtmlActivity;
import com.tzly.ctcyh.pay.pay_type_v.PayTypeActivity;
import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.service.RouterGlobal;

/**
 * 向外提供的路由规则
 */

public class PayUiRouter extends LibUiRouter implements IComponentRouter {

    /**
     * 单例
     */
    public static PayUiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final PayUiRouter INSTANCE = new PayUiRouter();
    }

    @Override
    protected String[] initHostToLib() {
        return new String[]{
                RouterGlobal.Host.pay_type_host,
                RouterGlobal.Host.coupon_list_host,
                RouterGlobal.Host.web_html_host,
                RouterGlobal.Host.coupon_detail_host,
                RouterGlobal.Host.coupon_status_host
        };
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (RouterGlobal.Host.pay_type_host.equals(host)) {
            intent.setClass(context, PayTypeActivity.class);
        } else if (RouterGlobal.Host.coupon_list_host.equals(host)) {
            intent.setClass(context, CouponListActivity.class);
        } else if (RouterGlobal.Host.web_html_host.equals(host)) {
            intent.setAction("com.tzly.ctcyh.pay.html_v.WebHtmlActivity");
            intent.setClass(context, WebHtmlActivity.class);
        } else if (RouterGlobal.Host.coupon_detail_host.equals(host)) {
            intent.setClass(context, CouponDetailActivity.class);
        } else if (RouterGlobal.Host.coupon_status_host.equals(host)) {
            intent.setClass(context, CouponStatusActivity.class);
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
        return !PayRouter.gotoByIsLogin();
    }

    @Override
    protected String verifySchemeToLib() {
        return RouterGlobal.Scheme.pay_scheme;
    }
}
