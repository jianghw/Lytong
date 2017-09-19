package com.tzly.annual.base.util;

import android.app.Application;
import android.content.Context;

/**
 * 如何地方获取context
 */
public class ContextHolder {

    static Context ApplicationContext;

    /* Public Methods */

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     */
    public static void init(Context context) {
        ApplicationContext = context;
    }

    public static Context getContext() {
        if (ApplicationContext != null) return ApplicationContext;

        try {
            Application application = (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null, (Object[]) null);
            if (application != null) {
                ApplicationContext = application;
                return application;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Application application = (Application) Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication")
                    .invoke(null, (Object[]) null);
            if (application != null) {
                ApplicationContext = application;
                return application;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("application context is recommend");
    }
}