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

    List<IComponentRouter> uiRouters = new ArrayList<>();
    /**
     * 优先级对应保存
     */
    HashMap<IComponentRouter, Integer> priorities = new HashMap<>();

    /**
     * 单例
     */
    public static UiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final UiRouter INSTANCE = new UiRouter();
    }

    @Override
    public void registerUI(IComponentRouter router) {
        registerUI(router, PRIORITY_NORMAL);
    }

    @Override
    public void registerUI(IComponentRouter router, int priority) {
        if (priorities.containsKey(router) && priority == priorities.get(router)) {
            return;
        }
        removeOldUIRouter(router);
        int i = 0;
        for (IComponentRouter temp : uiRouters) {
            Integer tp = priorities.get(temp);
            if (tp == null || tp <= priority) {
                break;
            }
            i++;
        }
        uiRouters.add(i, router);
        priorities.put(router, priority);
    }

    @Override
    public void unregisterUI(IComponentRouter router) {
        for (int i = 0; i < uiRouters.size(); i++) {
            if (router == uiRouters.get(i)) {
                uiRouters.remove(i);
                priorities.remove(router);
                break;
            }
        }
    }

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
        for (IComponentRouter temp : uiRouters) {
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
        for (IComponentRouter temp : uiRouters) {
            if (temp.verifyUri(uri)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去除旧的规则数据 用来以防万一
     */
    private void removeOldUIRouter(IComponentRouter router) {
        Iterator<IComponentRouter> iterator = uiRouters.iterator();
        while (iterator.hasNext()) {
            IComponentRouter tmp = iterator.next();
            if (tmp == router) {
                iterator.remove();
                priorities.remove(tmp);
            }
        }
    }
}
