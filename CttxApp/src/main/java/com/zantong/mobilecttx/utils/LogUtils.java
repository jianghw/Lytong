package com.zantong.mobilecttx.utils;

import android.util.Log;

import com.zantong.mobilecttx.BuildConfig;

/**
 * Log调试工具
 * @author Sandy
 * create at 16/6/1 下午5:54
 */
public class LogUtils {
	public static final String TAG = "tag";

	public static void i(String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.i(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void w(String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.w(TAG, msg);
		}
	}

	public static void d(String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.d(TAG, msg);
		}
	}

	public static void v(String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.v(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG) {
			Log.w(tag, msg);
		}
	}
}
