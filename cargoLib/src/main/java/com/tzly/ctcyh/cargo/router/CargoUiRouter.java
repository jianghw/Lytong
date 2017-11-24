package com.tzly.ctcyh.cargo.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.cargo.refuel_v.RechargeAgreementActivity;
import com.tzly.ctcyh.cargo.refuel_v.RefuelOilActivity;
import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.service.RouterGlobal;

/**
 * 向外提供的路由规则
 */

public class CargoUiRouter extends LibUiRouter implements IComponentRouter {

    /**
     * 单例
     */
    public static CargoUiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CargoUiRouter INSTANCE = new CargoUiRouter();
    }

    @Override
    protected String[] initHostToLib() {
        return new String[]{
                RouterGlobal.Host.recharge_host,
                RouterGlobal.Host.recharge_agree_host
        };
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (RouterGlobal.Host.recharge_host.equals(host)) {
            intent.setClass(context, RefuelOilActivity.class);
        } else if (RouterGlobal.Host.recharge_agree_host.equals(host)) {
            intent.setClass(context, RechargeAgreementActivity.class);
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
        return !CargoRouter.gotoByIsLogin();
    }

    @Override
    protected String verifySchemeToLib() {
        return RouterGlobal.Scheme.cargo_scheme;
    }
}
