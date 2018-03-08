package com.tzly.ctcyh.cargo.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.cargo.active_v.ActiveActivity;
import com.tzly.ctcyh.cargo.cc.drivingl.activity.DrivingCameraActivity;
import com.tzly.ctcyh.cargo.cc.vehicle.activity.VehicleCameraActivity;
import com.tzly.ctcyh.cargo.license_v.LicenseCargoActivity;
import com.tzly.ctcyh.cargo.refuel_v.BidOilActivity;
import com.tzly.ctcyh.cargo.refuel_v.DiscountOilActivity;
import com.tzly.ctcyh.cargo.refuel_v.FoldRefuelOilActivity;
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
                RouterGlobal.Host.recharge_agree_host,
                RouterGlobal.Host.driving_camera_host,
                RouterGlobal.Host.vehicle_camera_host,
                RouterGlobal.Host.active_host,
                RouterGlobal.Host.bid_oil_host,
                RouterGlobal.Host.discount_oil_host,
                RouterGlobal.Host.fold_oil_host,
                RouterGlobal.Host.license_cargo_host
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
        } else if (RouterGlobal.Host.driving_camera_host.equals(host)) {
            intent.setClass(context, DrivingCameraActivity.class);
        } else if (RouterGlobal.Host.vehicle_camera_host.equals(host)) {
            intent.setClass(context, VehicleCameraActivity.class);
        } else if (RouterGlobal.Host.active_host.equals(host)) {
            intent.setClass(context, ActiveActivity.class);
        } else if (RouterGlobal.Host.bid_oil_host.equals(host)) {
            intent.setClass(context, BidOilActivity.class);
        } else if (RouterGlobal.Host.discount_oil_host.equals(host)) {
            intent.setClass(context, DiscountOilActivity.class);
        } else if (RouterGlobal.Host.fold_oil_host.equals(host)) {
            intent.setClass(context, FoldRefuelOilActivity.class);
        } else if (RouterGlobal.Host.license_cargo_host.equals(host)) {
            intent.setClass(context, LicenseCargoActivity.class);
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
