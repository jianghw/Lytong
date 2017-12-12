package com.tzly.ctcyh.user;

import android.app.Application;

import com.tzly.ctcyh.router.util.Utils;

/**
 * 只为模块组件诞生
 */

public class UserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}