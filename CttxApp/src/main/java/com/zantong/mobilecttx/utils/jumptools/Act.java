package com.zantong.mobilecttx.utils.jumptools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.user.activity.LoginActivity;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Act {

    public static final String ACT_PARAM = "param";
    private static Act instance;

    private Act() {
    }

    public static synchronized Act getInstance() {
        if (instance == null) {
            instance = new Act();
        }
        return instance;
    }

    public void lauchIntent(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivity(intent);

    }

    /**
     * 该跳转需要验证登录状态
     *
     * @param packageContext
     * @param className
     */
    public void gotoIntent(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivity(intent);
    }

    public void gotoIntent(Context packageContext, Class<?> className, String str) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(ACT_PARAM, str);
        packageContext.startActivity(intent);
    }

    public void lauchIntentForResult(Activity packageContext, Class<?> className, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivityForResult(intent, requestCode);
    }

    public void lauchIntentToLoginForResult(Activity packageContext, Class<?> className, int requestCode) {

        if (!PublicData.getInstance().loginFlag && !TextUtils.isEmpty(PublicData.getInstance().userID)) {

            Intent intent = new Intent(packageContext, LoginActivity.class);
            packageContext.startActivity(intent);
        } else {
            Intent intent = new Intent(packageContext, className);
            packageContext.startActivityForResult(intent, requestCode);
        }
    }

    public void lauchIntentToLogin(Context packageContext, Class<?> className) {

        if (PublicData.getInstance().loginFlag && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            Intent intent = new Intent(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            PublicData.getInstance().className = className;
            Intent intent = new Intent(packageContext, LoginActivity.class);
            packageContext.startActivity(intent);
        }
    }

    public void launchLoginByIntent(Context packageContext, Class<?> className, Intent intent) {
        if (PublicData.getInstance().loginFlag
                && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            intent.setClass(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            PublicData.getInstance().className = className;
            intent.setClass(packageContext, LoginActivity.class);
            packageContext.startActivity(intent);
        }
    }

}
