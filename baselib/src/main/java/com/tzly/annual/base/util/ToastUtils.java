package com.tzly.annual.base.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 吐司显示
 */
public final class ToastUtils {

    public static void toastShort(String message) {
        initToast(ContextUtils.getContext(), message, Toast.LENGTH_SHORT);
    }

    private static void initToast(Context context, String message, int duration) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, duration).show();
    }

    public static void toastLong(String message) {
        initToast(ContextUtils.getContext(), message, Toast.LENGTH_LONG);
    }
}
