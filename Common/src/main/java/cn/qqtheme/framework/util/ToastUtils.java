package cn.qqtheme.framework.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司显示
 */
public final class ToastUtils {

    public static void showShort(Context context, String message) {
        initToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, int resId) {
        initToast(context, resId, Toast.LENGTH_SHORT);
    }

    private static void initToast(Context context, String message, int duration) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void initToast(Context context, int resId, int duration) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(String message) {
        initToast(ContextUtils.getContext(), message, Toast.LENGTH_SHORT);
    }
}
