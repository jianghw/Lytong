package com.tzly.ctcyh.service;

import android.app.Activity;
import android.content.Context;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface ICargoService {

    void gotoRechargeActivity(Context context);

    /**
     * 驾驶证扫描 110
     */
    void gotoDrivingCameraActivity(Activity activity);

    void gotoVehicleCameraActivity(Activity activity);
}