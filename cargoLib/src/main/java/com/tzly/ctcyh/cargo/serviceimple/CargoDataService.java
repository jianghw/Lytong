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
}
