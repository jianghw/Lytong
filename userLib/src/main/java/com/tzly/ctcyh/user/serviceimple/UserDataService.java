package com.tzly.ctcyh.user.serviceimple;

import android.support.annotation.NonNull;

import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.user.data_m.UserDataManager;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class UserDataService implements IUserService {

    private final UserDataManager mRepository;

    public UserDataService(@NonNull UserDataManager userDataManager) {
        mRepository = userDataManager;
    }

    /**
     * 是否登录
     */
    @Override
    public boolean isUserLogin() {
        return mRepository.isUserLogin();
    }
}
