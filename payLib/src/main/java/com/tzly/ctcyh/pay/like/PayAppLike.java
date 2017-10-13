package com.tzly.ctcyh.pay.like;


import com.tzly.ctcyh.pay.router.PayUiRouter;
import com.tzly.ctcyh.router.IApplicationLike;
import com.tzly.ctcyh.router.UiRouter;

/**
 * 通过插件进行代码的插入运行
 */

public class PayAppLike implements IApplicationLike {

    UiRouter uiRouter = UiRouter.getInstance();
    PayUiRouter mPayUiRouter = PayUiRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(mPayUiRouter);
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(mPayUiRouter);
    }
}
