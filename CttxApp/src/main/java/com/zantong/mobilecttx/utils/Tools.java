package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Tools {

    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

    public static boolean isStrEmpty(String str) {
        if ((str != null) && (str.trim().length() > 0) && (!"null".equals(str)) && (!"[]".equals(str))) {
            return false;
        } else {
            return true;
        }

    }

    public static String getYearDate() {
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyyMMdd");
        String str = yearDate.format(new Date());
        return str;
    }

    public static String getYearDateFormat(String format) {
        SimpleDateFormat yearDate = new SimpleDateFormat(format);
        String str = yearDate.format(new Date());
        return str;
    }

    public static String getTimeDate() {
        SimpleDateFormat timeDate = new SimpleDateFormat("hhmmss");
        String str = timeDate.format(new Date());
        return str;
    }

    public static String getYearDate(Date date) {
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy-MM-dd");
        String str = yearDate.format(date);
        return str;
    }

    public static String getTimeDate(Date time) {
        SimpleDateFormat timeDate = new SimpleDateFormat("hh-mm");
        String str = timeDate.format(time);
        return str;
    }

    public static String getTimeDateS() {
        SimpleDateFormat timeDate = new SimpleDateFormat("hhmmssSSS");
        String str = timeDate.format(new Date());
        return str;
    }

    public static String getIMEI(Context mCotnext) {
        String imei = "00000000";
        TelephonyManager telephonyManager = (TelephonyManager) mCotnext.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        return result;
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }

    /**
     * 版本比较
     *
     * @param localVersion 本地版本
     * @param netVersion   网络获取版本
     * @return -1表示需要更新
     */
    public static int compareVersion(String localVersion, String netVersion) {
        if (localVersion.equals(netVersion)) {
            return 0;
        }
        String[] version1Array = localVersion.split("\\.");
        String[] version2Array = netVersion.split("\\.");
        int versiorIndex = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        while (versiorIndex < minLen
                && (diff = Integer.parseInt(version1Array[versiorIndex])
                - Integer.parseInt(version2Array[versiorIndex])) == 0) {
            versiorIndex++;
        }
        if (diff == 0) {
            for (int i = versiorIndex; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = versiorIndex; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 功能：获取程序版本名
     *
     * @param ctx
     * @return verName 版本名称
     */
    public static String getVerName(Context ctx) {
        return String.valueOf(getPi(ctx).versionName);
    }

    private static PackageInfo getPi(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }//getPackageName表示获取当前ctx所在的包的名称，0表示获取版本信息
        return pi;
    }


    /**
     * 安装apk
     */
    public static void installApk(Context context, String filename) {
        File file = new File(filename);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), type);
        context.startActivity(intent);
        Log.e("success", "the end");
        // 判断apk是否安装过
        PackageInfo pm;
        try {
            pm = context.getPackageManager()
                    .getPackageInfo(getAppInfo(context), 0);
            Log.i("version", String.valueOf(pm));
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获得包名
     *
     * @return
     */
    private static String getAppInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "   " + versionName + "  " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

    public static String getProvinceStr(int primaryCode) {
        String provinceStr = "沪";
        switch (primaryCode) {
            case 101:
                provinceStr = "京";
                break;
            case 102:
                provinceStr = "津";
                break;
            case 103:
                provinceStr = "冀";
                break;
            case 104:
                provinceStr = "晋";
                break;
            case 105:
                provinceStr = "蒙";
                break;
            case 106:
                provinceStr = "辽";
                break;
            case 107:
                provinceStr = "吉";
                break;
            case 108:
                provinceStr = "黑";
                break;
            case 109:
                provinceStr = "沪";
                break;
            case 110:
                provinceStr = "苏";
                break;
            case 111:
                provinceStr = "浙";
                break;
            case 112:
                provinceStr = "皖";
                break;
            case 113:
                provinceStr = "闽";
                break;
            case 114:
                provinceStr = "赣";
                break;
            case 115:
                provinceStr = "鲁";
                break;
            case 116:
                provinceStr = "豫";
                break;
            case 117:
                provinceStr = "鄂";
                break;
            case 118:
                provinceStr = "湘";
                break;
            case 119:
                provinceStr = "粤";
                break;
            case 120:
                provinceStr = "桂";
                break;
            case 121:
                provinceStr = "琼";
                break;
            case 122:
                provinceStr = "渝";
                break;
            case 123:
                provinceStr = "川";
                break;
            case 124:
                provinceStr = "贵";
                break;
            case 125:
                provinceStr = "云";
                break;
            case 126:
                provinceStr = "藏";
                break;
            case 127:
                provinceStr = "陕";
                break;
            case 128:
                provinceStr = "甘";
                break;
            case 129:
                provinceStr = "青";
                break;
            case 130:
                provinceStr = "宁";
                break;
            case 131:
                provinceStr = "新";
                break;
            case 132:
                provinceStr = "台";
                break;
            case 133:
                provinceStr = "京";
                break;
        }
        return provinceStr;
    }
}
