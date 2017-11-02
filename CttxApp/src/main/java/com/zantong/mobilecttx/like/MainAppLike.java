package com.zantong.mobilecttx.like;


import com.tzly.ctcyh.router.IApplicationLike;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.IMainService;
import com.zantong.mobilecttx.router.MainUiRouter;
import com.zantong.mobilecttx.serviceimple.MainDataService;

/**
 * 通过插件进行代码的插入运行
 */

public class MainAppLike implements IApplicationLike {

    UiRouter uiRouter = UiRouter.getInstance();
    MainUiRouter mMainUiRouter = MainUiRouter.getInstance();
    ServiceRouter mServiceRouter = ServiceRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(mMainUiRouter);
        mServiceRouter.addService(IMainService.class.getSimpleName(), new MainDataService());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(mMainUiRouter);
        mServiceRouter.removeService(IMainService.class.getSimpleName());
    }
}
