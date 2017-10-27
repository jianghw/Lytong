package com.tzly.ctcyh.user.like;


import com.tzly.ctcyh.router.IApplicationLike;
import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.user.data_m.InjectionRepository;
import com.tzly.ctcyh.user.router.UserUiRouter;
import com.tzly.ctcyh.user.serviceimple.UserDataService;

import static com.tzly.ctcyh.router.util.Utils.getContext;

/**
 * 通过插件进行代码的插入运行
 */

public class UserAppLike implements IApplicationLike {

    UiRouter mUiRouter = UiRouter.getInstance();
    UserUiRouter mUserUiRouter = UserUiRouter.getInstance();
    ServiceRouter mServiceRouter = ServiceRouter.getInstance();

    @Override
    public void onCreate() {
        mUiRouter.registerUI(mUserUiRouter);
        mServiceRouter.addService(IUserService.class.getSimpleName(),
                new UserDataService(InjectionRepository.provideRepository(getContext())));
    }

    @Override
    public void onStop() {
        mUiRouter.unregisterUI(mUserUiRouter);
        mServiceRouter.removeService(IUserService.class.getSimpleName());
    }
}
