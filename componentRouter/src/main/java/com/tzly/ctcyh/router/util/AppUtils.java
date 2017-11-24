package com.tzly.ctcyh.router.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by jianghw on 2017/10/27.
 * Description:
 * Update by:
 * Update day:
 */

public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        String deviceId = null;
        TelephonyManager telephonyManager =
                (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(deviceId)) {
            StringBuffer sb = new StringBuffer();
            String allChar = "0123456789";
            Random random = new Random();
            for (int i = 0; i < 12; i++) {
                sb.append(allChar.charAt(
                        random.nextInt(allChar.length())));
            }
            deviceId = sb.toString();
        }
        return deviceId;
    }

    /**
     * 判断App是否安装
     *
     * @param action   action
     * @param category category
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = Utils.getContext().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断App是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && getLaunchAppIntent(packageName) != null;
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return Utils.getContext().getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 判断App是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager)
                Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getContext().getPackageName());
            }
        }
        return false;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(Utils.getContext().getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本码
     *
     * @return App版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getContext().getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param packageName 包名
     * @return App版本码
     */
    public static int getAppVersionCode(String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo =
                        packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }
}
