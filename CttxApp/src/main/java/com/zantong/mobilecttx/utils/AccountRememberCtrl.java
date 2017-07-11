package com.zantong.mobilecttx.utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/5/25.
 */
public class AccountRememberCtrl extends SharedFile {


    public static final String Token = "token";
    public static final String ID = "id";
    public static final String DefaultNumber = "userSex";
    public static final String Personal = "personal";
    public static final String EFFECT = "effect";
    public static final String LoginAD = "LoginAD";
    public static final String ChangTongFlag = "ChangTongFlag";

    public static String getToken(Context context) {
        return getValue(context, Token);
    }

    public static String getID(Context context) {
        return getValue(context, ID);
    }

    public static String getDefaultNumber(Context context) {
        return getValue(context, DefaultNumber);
    }

    public static String getPersonal(Context context) {
        return getValue(context, Personal);
    }

    public static String getEFFECT(Context context) {
        return getValue(context, EFFECT);
    }

    public static String getLoginAD(Context context) {
        return getValue(context, LoginAD);
    }

    public static String getChangTongFlag(Context context) {
        return getValue(context, ChangTongFlag);
    }

    public static void saveToken(Context context, String value) {
        save(context, Token, value);
    }

    public static void saveID(Context context, String value) {
        save(context, ID, value);
    }

    public static void saveDefaultNumber(Context context, String value) {
        save(context, DefaultNumber, value);
    }

    public static void saveLoginAD(Context context, String value) {
        save(context, LoginAD, value);
    }

    public static void saveChangTongFlag(Context context, String value) {
        save(context, ChangTongFlag, value);
    }

    public static void savePersonal(Context context, String value) {
        save(context, Personal, value);
    }

    public static void saveEFFECT(Context context, String value) {
        save(context, EFFECT, value);
    }

    public static void nosavePersonal(Context context) {
        noSave(context, Personal);
    }

    public static void nosaveDefaultNumber(Context context) {
        noSave(context, DefaultNumber);
    }

    public static void nosaveLoginAD(Context context) {
        noSave(context, LoginAD);
    }

    public static void nosaveChangTongFlag(Context context) {
        noSave(context, ChangTongFlag);
    }

    public static String getValue(Context context, String name) {
        sp = getSp(context);
        return sp.getString(name, "");
    }

    protected static void save(Context context, String name, String value) {
        sp = getSp(context);
        sp.edit().putString(name, value).commit();
//     	Log4j.debug("phoneNum saved into FILE_SAVE!");
    }

    protected static void noSave(Context context, String name) {
        sp = getSp(context);
        sp.edit().remove(name).commit();
//    	Log4j.debug("phoneNum cleared from FILE_SAVE!");
    }

}
