package com.tzly.ctcyh.router;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * 路由注册机
 */

public class ServiceRouter {

    private HashMap<String, Object> mServiceMap = new HashMap<>();
    //注册的组件的集合
    private static HashMap<String, IApplicationLike> mComponentMap = new HashMap<>();

    /**
     * 单例
     */
    public static ServiceRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ServiceRouter INSTANCE = new ServiceRouter();
    }

    /**
     * 注册组件
     *
     * @param classname 组件名
     */
    public void registerComponent(@Nullable String classname) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (mComponentMap.keySet().contains(classname)) {
            return;
        }
        try {
            Class clazz = Class.forName(classname);
            IApplicationLike applicationLike = (IApplicationLike) clazz.newInstance();
            applicationLike.onCreate();
            mComponentMap.put(classname, applicationLike);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反注册组件
     *
     * @param classname 组件名
     */
    public void unregisterComponent(@Nullable String classname) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (mComponentMap.keySet().contains(classname)) {
            mComponentMap.get(classname).onStop();
            mComponentMap.remove(classname);
            return;
        }
        try {
            Class clazz = Class.forName(classname);
            IApplicationLike applicationLike = (IApplicationLike) clazz.newInstance();
            applicationLike.onStop();
            mComponentMap.remove(classname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加组件间的页面获取接口
     */
    public synchronized void addService(String serviceName, Object serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }
        mServiceMap.put(serviceName, serviceImpl);
    }

    public synchronized Object getService(String serviceName) {
        if (serviceName == null) {
            return null;
        }
        return mServiceMap.get(serviceName);
    }

    public synchronized void removeService(String serviceName) {
        if (serviceName == null) {
            return;
        }
        mServiceMap.remove(serviceName);
    }

}
