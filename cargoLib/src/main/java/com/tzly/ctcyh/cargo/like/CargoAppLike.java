package com.tzly.ctcyh.cargo.like;


import com.tzly.ctcyh.cargo.router.CargoUiRouter;
import com.tzly.ctcyh.cargo.serviceimple.CargoDataService;
import com.tzly.ctcyh.router.IApplicationLike;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.ICargoService;

/**
 * 通过插件进行代码的插入运行
 */

public class CargoAppLike implements IApplicationLike {

    UiRouter uiRouter = UiRouter.getInstance();
    CargoUiRouter mCargoUiRouter = CargoUiRouter.getInstance();
    ServiceRouter mServiceRouter = ServiceRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(mCargoUiRouter);
        mServiceRouter.addService(ICargoService.class.getSimpleName(), new CargoDataService());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(mCargoUiRouter);
        mServiceRouter.removeService(ICargoService.class.getSimpleName());
    }
}
