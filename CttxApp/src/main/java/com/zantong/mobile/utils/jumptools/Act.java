package com.zantong.mobile.utils.jumptools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.login_v.OldLoginActivity;

import com.tzly.annual.base.global.JxGlobal;

/**
 * Created by Administrator on 2016/5/4.
 */
public class Act {

    private static Act instance;

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

    public void gotoIntent(Context packageContext, Class<?> className) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivity(intent);
    }

    public void gotoIntent(Context packageContext, Class<?> className, String str) {
        Intent intent = new Intent(packageContext, className);
        intent.putExtra(JxGlobal.putExtra.common_extra, str);
        packageContext.startActivity(intent);
    }

    /**
     * 该跳转需要验证登录状态
     */
    public void gotoIntentLogin(Context packageContext, Class<?> className) {

        if (PublicData.getInstance().loginFlag
                && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            Intent intent = new Intent(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            PublicData.getInstance().className = className;
            Intent intent = new Intent(packageContext, OldLoginActivity.class);
            packageContext.startActivity(intent);
        }
    }

    public void gotoIntentForResult(Activity packageContext, Class<?> className, int requestCode) {
        Intent intent = new Intent(packageContext, className);
        packageContext.startActivityForResult(intent, requestCode);
    }

    public void gotoLoginForResult(Activity packageContext, Class<?> className, int requestCode) {

        if (!PublicData.getInstance().loginFlag
                && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            Intent intent = new Intent(packageContext, OldLoginActivity.class);
            packageContext.startActivity(intent);
        } else {
            Intent intent = new Intent(packageContext, className);
            packageContext.startActivityForResult(intent, requestCode);
        }
    }

    public void gotoLoginByIntent(Context packageContext, Class<?> className, Intent intent) {
        if (PublicData.getInstance().loginFlag
                && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            intent.setClass(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            PublicData.getInstance().className = className;
            intent.setClass(packageContext, OldLoginActivity.class);
            packageContext.startActivity(intent);
        }
    }

    public void gotoLoginForResult(Context packageContext, Class<?> className, Intent intent) {
        if (PublicData.getInstance().loginFlag
                && !TextUtils.isEmpty(PublicData.getInstance().userID)) {
            intent.setClass(packageContext, className);
            packageContext.startActivity(intent);
        } else {
            PublicData.getInstance().className = className;
            intent.setClass(packageContext, OldLoginActivity.class);
            packageContext.startActivity(intent);
        }
    }

}
