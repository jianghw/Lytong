package com.zantong.mobilecttx.utils;

/**
 * Created by zhengyingbing on 17/3/14.
 */

public class OnClickUtils {

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 2000) {
            lastClickTime = time;
            return true;
        }
        return false;
    }
}
