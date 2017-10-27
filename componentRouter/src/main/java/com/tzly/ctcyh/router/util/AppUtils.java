package com.tzly.ctcyh.router.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

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
        Context context = Utils.getContext();
        String deviceId = "";
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }
}
