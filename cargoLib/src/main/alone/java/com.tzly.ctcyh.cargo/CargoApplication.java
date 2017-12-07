package com.tzly.ctcyh.cargo;

import android.support.multidex.MultiDexApplication;

import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.rea.Des3;


/**
 * 只为模块组件诞生 一切自己单干时用
 */

public class CargoApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        LogUtils.initLogUtils(true);

        ToastUtils.toastShort(Des3.decode("8cxaZ0iOv8bGUs4RdU6fEA=="));
    }

}