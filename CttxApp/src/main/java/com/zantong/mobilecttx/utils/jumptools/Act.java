package com.zantong.mobilecttx.utils.jumptools;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.service.MemoryData;
import com.zantong.mobilecttx.router.MainRouter;

import cn.qqtheme.framework.global.JxGlobal;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Act {

    private static Act instance;

    public static synchronized Act getInstance() {
        if (instance == null) {
            instance = new Act();
        }
        return instance;
    }

    public void lauchIntent(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivity(intent);
    }

    public void gotoIntent(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivity(intent);
    }

    public void gotoIntent(Context packageContext, Class<?> className, String str) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(JxGlobal.putExtra.common_extra, str);
        packageContext.startActivity(intent);
    }

    /**
     * 该跳转需要验证登录状态
     */
    public void gotoIntentLogin(Context packageContext, Class<?> className) {

        if (MemoryData.getInstance().isMainLogin()) {
            Intent intent = new Intent(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            MainRouter.gotoLoginActivity(packageContext);
        }
    }

    public void gotoLoginByIntent(Context packageContext, Class<?> className, Intent intent) {
        if (MemoryData.getInstance().isMainLogin()) {
            intent.setClass(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            MainRouter.gotoLoginActivity(packageContext);
        }
    }


}
