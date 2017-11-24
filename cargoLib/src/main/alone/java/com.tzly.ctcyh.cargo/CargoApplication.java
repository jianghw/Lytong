package com.tzly.ctcyh.cargo;

import android.support.multidex.MultiDexApplication;

import com.tzly.ctcyh.router.util.Utils;


/**
 * 只为模块组件诞生 一切自己单干时用
 */

public class CargoApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}