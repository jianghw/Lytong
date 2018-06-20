package com.zantong.mobilecttx.home_v;

import android.support.annotation.IdRes;
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

        gotoByUrl(url, "热门优惠", activity);
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

        gotoByStatistId(url, "热门优惠", keyId, activity);
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

    /**
     * 注意 activity 和 fregment 的区别
     */
    public static void gotoAdvModuleFragment(FragmentManager fragmentManager,
                                             @IdRes int containerViewId, String carNum) {

        Fragment fragment = fragmentManager.findFragmentById(containerViewId);
        if (fragment == null) {
            AdvModuleFragment moduleFragment = AdvModuleFragment.newInstance(carNum);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerViewId, moduleFragment).commitNow();
        } else if (!(fragment instanceof AdvModuleFragment)) {
            AdvModuleFragment moduleFragment = AdvModuleFragment.newInstance(carNum);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerViewId, moduleFragment).commitNow();
            transaction.add(containerViewId, moduleFragment).commitNow();
        } else {
            AdvModuleFragment moduleFragment = (AdvModuleFragment) fragment;
            moduleFragment.relodaData(carNum);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerViewId, moduleFragment).commitNow();
        }

      /*  if (fragment == null) {
            fragment = manager.findFragmentByTag(tag_fgt);
        }
        if (fragment instanceof AdvModuleFragment) {
            AdvModuleFragment moduleFragment = (AdvModuleFragment) fragment;
        } else {
            ToastUtils.toastShort("路由规则遗失,程序员GG会尽快处理");
        }*/
    }

}
