package com.tzly.ctcyh.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * ui 跳转路由规则
 */

public class UiRouter implements IUiRouter {
    /**
     * 地址规则集合
     */
    List<IComponentRouter> uiRouterList = new ArrayList<>();
    /**
     * 优先级对应保存
     */
    HashMap<IComponentRouter, Integer> prioritiesMap = new HashMap<>();

    /**
     * 单例
     */
    public static UiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final UiRouter INSTANCE = new UiRouter();
    }

    /**
     * 注册UI 优先级 实现接口逻辑
     *
     * @param router 传入定义scheme
     */
    @Override
    public void registerUI(IComponentRouter router) {
        registerUI(router, PRIORITY_NORMAL);
    }

    @Override
    public void registerUI(IComponentRouter router, int priority) {
        if (prioritiesMap.containsKey(router) && priority == prioritiesMap.get(router)) {
            return;
        }
        removeOldUIRouter(router);
        int i = 0;
        for (IComponentRouter temp : uiRouterList) {
            Integer tp = prioritiesMap.get(temp);
            if (tp == null || tp <= priority) {
                break;
            }
            i++;
        }
        uiRouterList.add(i, router);
        prioritiesMap.put(router, priority);
    }

    /**
     * 去除旧的地址信息数据
     */
    private void removeOldUIRouter(IComponentRouter router) {
        Iterator<IComponentRouter> iterator = uiRouterList.iterator();
        while (iterator.hasNext()) {
            IComponentRouter tmp = iterator.next();
            if (tmp == router) {
                iterator.remove();
                prioritiesMap.remove(tmp);
            }
        }
    }

    @Override
    public void unregisterUI(IComponentRouter router) {
        for (int i = 0; i < uiRouterList.size(); i++) {
            if (router == uiRouterList.get(i)) {
                uiRouterList.remove(i);
                prioritiesMap.remove(router);
                break;
            }
        }
    }

    /**
     * 最先执行方法
     */
    @Override
    public boolean openUri(Context context, String url, Bundle bundle) {
        url = url.trim();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains("://") && (!url.startsWith("tel:") ||
                    !url.startsWith("smsto:") ||
                    !url.startsWith("file:"))) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            return openUri(context, uri, bundle);
        }
        return true;
    }

    /**
     * 此方法最终会调用 子类实现
     */
    @Override
    public boolean openUri(Context context, Uri uri, Bundle bundle) {
        for (IComponentRouter temp : uiRouterList) {
            try {
                if (temp.verifyUri(uri) && temp.openUri(context, uri, bundle)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean verifyUri(Uri uri) {
        for (IComponentRouter temp : uiRouterList) {
            if (temp.verifyUri(uri)) {
                return true;
            }
        }
        return false;
    }
}
