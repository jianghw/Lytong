package com.tzly.ctcyh.router.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by jianghw on 2017/6/21.
 * Description:初始化工具类
 * Update by:
 * Update day:
 */

public final class Utils {

    /**
     * 增加过滤规则
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
