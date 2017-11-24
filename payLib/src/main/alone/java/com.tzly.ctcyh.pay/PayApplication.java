package com.tzly.ctcyh.pay;

import android.support.multidex.MultiDexApplication;

import com.tzly.ctcyh.router.util.Utils;

/**
 * 只为模块组件诞生 一切自己单干时用
 */

public class PayApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }
}