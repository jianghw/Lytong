package com.tzly.ctcyh.pay.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.pay.html_v.Html5Activity;
import com.tzly.ctcyh.pay.coupon_v.CouponListActivity;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.pay_type_v.PayTypeActivity;
import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.service.IUserService;

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
                PayGlobal.Host.pay_type_host,
                PayGlobal.Host.coupon_list_host,
                PayGlobal.Host.html_5_host
        };
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (PayGlobal.Host.pay_type_host.equals(host)) {
            intent.setClass(context, PayTypeActivity.class);
        } else if (PayGlobal.Host.coupon_list_host.equals(host)) {
            intent.setClass(context, CouponListActivity.class);
        } else if (PayGlobal.Host.html_5_host.equals(host)) {
            intent.setAction("com.tzly.ctcyh.pay.html_v.Html5Activity");
            intent.setClass(context, Html5Activity.class);
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
        return loginActivity();
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
        return PayGlobal.Scheme.pay_scheme;
    }
}
