package com.tzly.ctcyh.router.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @link https://github.com/orhanobut/logger
 */
public final class LogUtils {

    private static final String TAG = "Logger";

    /**
     * 环境初始化Log显示
     */
    public static void initLogUtils(final boolean isDebug) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });
    }

    public static void i(int msg) {
        Logger.i(String.valueOf(msg));
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void i(String tag, String msg) {
        Logger.i(tag, msg);
    }

    public static void e(int msg) {
        Logger.e(String.valueOf(msg));
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(String tag, String msg) {
        Logger.e(tag, msg);
    }

    public static void d(int msg) {
        Logger.d(String.valueOf(msg));
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void d(String tag, String msg) {
        Logger.d(tag, msg);
    }

    public static void v(String msg) {
        Logger.v(msg);
    }

    public static void v(String tag, String msg) {
        Logger.v(tag, msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void w(String tag, String msg) {
        Logger.w(tag, msg);
    }

    public static void json(String meg) {
        Logger.json(meg);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void jsonObject(Object object) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String string = gson.toJson(object);
        json(string);
    }

}



