package com.zantong.mobilecttx.like;


import com.tzly.ctcyh.router.IApplicationLike;
import com.tzly.ctcyh.router.UiRouter;
import com.zantong.mobilecttx.router.MainUiRouter;

/**
 * 通过插件进行代码的插入运行
 */

public class MainAppLike implements IApplicationLike {

    UiRouter uiRouter = UiRouter.getInstance();
    MainUiRouter mMainUiRouter = MainUiRouter.getInstance();

    @Override
    public void onCreate() {
        uiRouter.registerUI(mMainUiRouter);
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI(mMainUiRouter);
    }
}
