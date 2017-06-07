package com.zantong.mobilecttx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import java.util.concurrent.ExecutorService;

/**
 * 作者：王海洋
 * 时间：2016/6/16 14:20
 */
public class CordovaContext extends ContextWrapper implements CordovaInterface {
    CordovaInterface cordova;

    public CordovaContext(Context base, CordovaInterface cordova) {
        super(base);
        this.cordova = cordova;
    }
    public void startActivityForResult(CordovaPlugin command,
                                       Intent intent, int requestCode) {
        cordova.startActivityForResult(command, intent, requestCode);
    }
    public void setActivityResultCallback(CordovaPlugin plugin) {
        cordova.setActivityResultCallback(plugin);
    }
    public Activity getActivity() {
        return cordova.getActivity();
    }
    public Object onMessage(String id, Object data) {
        return cordova.onMessage(id, data);
    }
    public ExecutorService getThreadPool() {
        return cordova.getThreadPool();
    }

    @Override
    public void requestPermission(CordovaPlugin cordovaPlugin, int i, String s) {

    }

    @Override
    public void requestPermissions(CordovaPlugin cordovaPlugin, int i, String[] strings) {

    }

    @Override
    public boolean hasPermission(String s) {
        return false;
    }
}
