package com.tzly.ctcyh.cargo.serviceimple;

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
}
