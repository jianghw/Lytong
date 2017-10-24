package com.tzly.ctcyh.router.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司显示
 */
public final class ToastUtils {

    public static void toastShort(String message) {
        initToast(Utils.getContext(), message, Toast.LENGTH_SHORT);
    }

    private static void initToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void toastLong(String message) {
        initToast(Utils.getContext(), message, Toast.LENGTH_LONG);
    }
}
