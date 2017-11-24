package com.tzly.ctcyh.pay.like;


import com.tzly.ctcyh.pay.router.PayUiRouter;
import com.tzly.ctcyh.pay.serviceimple.PayDataService;
import com.tzly.ctcyh.router.IApplicationLike;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.IPayService;

/**
 * 通过插件进行代码的插入运行
 */

public class PayAppLike implements IApplicationLike {

    UiRouter uiRouter = UiRouter.getInstance();
    PayUiRouter mPayUiRouter = PayUiRouter.getInstance();
    ServiceRouter mServiceRouter = ServiceRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(mPayUiRouter);
        mServiceRouter.addService(IPayService.class.getSimpleName(), new PayDataService());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(mPayUiRouter);
        mServiceRouter.removeService(IPayService.class.getSimpleName());
    }
}
