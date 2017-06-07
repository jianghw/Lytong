package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 吐司提示工具类
 * @author Sandy
 * create at 16/6/1 下午5:54
 */
public class ToastUtils {
	public static Toast toast;

	public static void showShort(Context context, String message) {
		initToast(context, message, Toast.LENGTH_SHORT);
	}

	public static void showShort(Context context, int resId) {
		initToast(context, resId, Toast.LENGTH_SHORT);
	}

	private static void initToast(Context context, String message, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	private static void initToast(Context context, int resId, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		} else {
			toast.setText(resId);
		}
		toast.show();
	}
}
