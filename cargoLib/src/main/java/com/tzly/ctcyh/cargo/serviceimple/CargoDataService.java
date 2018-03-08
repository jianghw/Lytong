package com.tzly.ctcyh.cargo.serviceimple;

import android.app.Activity;
import android.content.Context;

import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.service.ICargoService;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class CargoDataService implements ICargoService {
    /**
     * 加油充值页面
     */
    @Override
    public void gotoRechargeActivity(Context context) {
        CargoRouter.gotoRechargeActivity(context);
    }

    /**
     * 驾驶证扫描 110
     */
    @Override
    public void gotoDrivingCameraActivity(Activity context) {
        CargoRouter.gotoDrivingCameraActivity(context);
    }

    /**
     * 行驶证证扫描 110
     */
    @Override
    public void gotoVehicleCameraActivity(Activity context) {
        CargoRouter.gotoVehicleCameraActivity(context);
    }

    /**
     * 活动页面逻辑
     */
    @Override
    public void gotoActiveActivity(Context context, String channel, String date) {
        CargoRouter.gotoActiveActivity(context, channel, date);
    }

    /**
     * 97 加油
     */
    @Override
    public void gotoDiscountOilActivity(Context context) {
        CargoRouter.gotoDiscountOilActivity(context);
    }

    /**
     * 97申请办卡
     */
    @Override
    public void gotoBidOilActivity(Context activity) {
        CargoRouter.gotoBidOilActivity(activity);
    }

    /**
     * 97 加油 申请办卡
     */
    @Override
    public void gotoFoldOilActivity(Context context) {
        CargoRouter.gotoFoldOilActivity(context);
    }
    /**
     * 驾照查分
     */
    @Override
    public void gotoLicenseCargoActivity(Context context) {
        CargoRouter.gotoLicenseCargoActivity(context);
    }
}
