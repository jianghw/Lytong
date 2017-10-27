package com.tzly.ctcyh.router;

import android.app.Activity;
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
        //含有且优先级无变化时直接返回
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
     * 去除旧原有的相同信息数据
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
     * false-->执行出错
     * true--> 向下执行
     */
    @Override
    public boolean openUriBundle(Context context, String url, Bundle bundle) {
        url = url.trim();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains("://") && (!url.startsWith("tel:") ||
                    !url.startsWith("smsto:") ||
                    !url.startsWith("file:"))) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            return openUriBundle(context, uri, bundle);
        }
        return false;
    }

    /**
     * 此方法最终会调用 IComponentRouter实现类中方法
     */
    @Override
    public boolean openUriBundle(Context context, Uri uri, Bundle bundle) {
        for (IComponentRouter temp : uiRouterList) {
            try {
                if (temp.verifyUri(uri) && temp.openUriBundle(context, uri, bundle)) {
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

    @Override
    public boolean openUriForResult(Activity activity, String url, Bundle bundle, int requestCode) {
        url = url.trim();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains("://") && (!url.startsWith("tel:") ||
                    !url.startsWith("smsto:") ||
                    !url.startsWith("file:"))) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            return openUriForResult(activity, uri, bundle, requestCode);
        }
        return false;
    }

    @Override
    public boolean openUriForResult(Activity activity, Uri uri, Bundle bundle, int requestCode) {
        for (IComponentRouter temp : uiRouterList) {
            try {
                if (temp.verifyUri(uri) && temp.openUriForResult(activity, uri, bundle, requestCode)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
