package com.tzly.ctcyh.cargo.refuel_v;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tzly.ctcyh.router.util.ToastUtils;

public class ShareUtils {

    /**
     * 微信分享业务
     */
    public static void showWechat(FragmentActivity activity, String imgUrl, String codeUrl, int type) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_wechat");

        if (fragment == null || !(fragment instanceof ShareWechatFragment)) {
            //创建fragment但是不绘制UI
            ShareWechatFragment wechatFragment = ShareWechatFragment.newInstance(imgUrl, codeUrl, type);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(wechatFragment, "router_wechat").commitNow();
            wechatFragment.toShareBitmap();
        } else if (fragment != null && fragment instanceof ShareWechatFragment) {
            fragment = manager.findFragmentByTag("router_wechat");
            ShareWechatFragment wechatFragment = (ShareWechatFragment) fragment;
            wechatFragment.reloadData(imgUrl, codeUrl, type);
            wechatFragment.toShareBitmap();
        } else {
            ToastUtils.toastShort("路由规则遗失,程序员GG会尽快处理");
        }
    }
}
