package cn.qqtheme.framework.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by jianghw on 2017/6/21.
 * Description:初始化工具类
 * Update by:
 * Update day:
 */

public final class ContextUtils {

    /**
     * 增加过滤规则
     */
    @SuppressLint("StaticFieldLeak")
    private static Context ApplicationContext;

    private ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        ContextUtils.ApplicationContext = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (ApplicationContext != null) return ApplicationContext;
        else return getRadiateContext();
    }

    public static Context getRadiateContext() {
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
