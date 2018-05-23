package com.zantong.mobilecttx.home_v;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tzly.ctcyh.router.util.ToastUtils;

/**
 * 路由统计类
 */

public class RouterUtils {

    /**
     * 只跳转
     */
    public static void gotoByUrl(String url, FragmentActivity activity) {

        gotoByUrl(url, "商品推荐", activity);
    }

    public static void gotoByUrl(String url, String title, FragmentActivity activity) {

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_fgt");

        if (fragment == null || !(fragment instanceof RouterFragment)) {
            //创建fragment但是不绘制UI
            RouterFragment routerFragment = RouterFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(routerFragment, "router_fgt").commitNow();
        }
        if (fragment == null) {
            fragment = manager.findFragmentByTag("router_fgt");
        }
        if (fragment instanceof RouterFragment) {
            RouterFragment routerFragment = (RouterFragment) fragment;
            routerFragment.clickItemData(url, title);
        } else {
            ToastUtils.toastShort("找不到点击路由,程序员GG会尽快处理");
        }
    }

    /**
     * 广告
     */
    public static void gotoByAdvId(String url, String title, String keyId,
                                   FragmentActivity activity) {

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_fgt");

        if (fragment == null || !(fragment instanceof RouterFragment)) {
            //创建fragment但是不绘制UI
            RouterFragment routerFragment = RouterFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(routerFragment, "router_fgt").commitNow();
        }
        if (fragment == null) {
            fragment = manager.findFragmentByTag("router_fgt");
        }
        if (fragment instanceof RouterFragment) {
            RouterFragment routerFragment = (RouterFragment) fragment;
            routerFragment.advClickItemData(url, title, keyId);
        } else {
            ToastUtils.toastShort("找不到点击路由,程序员GG会尽快处理");
        }
    }

    /**
     * 统计
     */
    public static void gotoByStatistId(String url, String keyId, FragmentActivity activity) {

        gotoByStatistId(url, "商品推荐", keyId, activity);
    }

    public static void gotoByStatistId(String url, String title,
                                       String keyId, FragmentActivity activity) {

        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_fgt");

        if (fragment == null || !(fragment instanceof RouterFragment)) {
            //创建fragment但是不绘制UI
            RouterFragment routerFragment = RouterFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(routerFragment, "router_fgt").commitNow();
        }
        if (fragment == null) {
            fragment = manager.findFragmentByTag("router_fgt");
        }
        if (fragment instanceof RouterFragment) {
            RouterFragment routerFragment = (RouterFragment) fragment;
            routerFragment.statistClickItemData(url, title, keyId);
        } else {
            ToastUtils.toastShort("找不到点击路由,程序员GG会尽快处理");
        }
    }

    /**
     * 是否唤起 活动弹框
     */
    public static void showDialogActive(String channel, String date, FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_fgt");

        if (fragment == null || !(fragment instanceof RouterFragment)) {
            //创建fragment但是不绘制UI
            RouterFragment routerFragment = RouterFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(routerFragment, "router_fgt").commitNow();
        }
        if (fragment == null) {
            fragment = manager.findFragmentByTag("router_fgt");
        }
        if (fragment instanceof RouterFragment) {
            RouterFragment routerFragment = (RouterFragment) fragment;
            routerFragment.activeToShow(channel, date);
        } else {
            ToastUtils.toastShort("路由规则遗失,程序员GG会尽快处理");
        }
    }

}
