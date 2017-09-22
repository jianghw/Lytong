package com.zantong.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author liuxm
 */

public class SharedFile {
    protected static final String FILE_SP = "YH";

    protected static SharedPreferences sp = null;

    protected static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(FILE_SP, 0);
    }

    protected static void save(Context context, String key, String value) {
        sp = getSp(context);
        sp.edit().putString(key, value).commit();
//     	Log4j.debug(key+ " saved into FILE_SAVE!");
    }

    protected static void noSave(Context context, String key) {
        sp = getSp(context);
        sp.edit().remove(key).commit();
//    	Log4j.debug(key+ " cleared from FILE_SAVE!");
    }

    protected static void save(Context context, String key, boolean value) {
        sp = getSp(context);
        sp.edit().putBoolean(key, value).commit();
//		Log4j.debug(key+ " saved into FILE_SAVE!");
    }

    protected static void noSave(Context context, String key, boolean value) {
        sp = getSp(context);
        sp.edit().putBoolean(key, value).commit();
//    	Log4j.debug(key + " saved into FILE_SAVE!");
    }
}
