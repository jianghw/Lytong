package com.tzly.ctcyh.pay;

import android.app.Application;

import com.tzly.ctcyh.router.util.Utils;

/**
 * 只为模块组件诞生 一切自己单干时用
 */

public class PayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}