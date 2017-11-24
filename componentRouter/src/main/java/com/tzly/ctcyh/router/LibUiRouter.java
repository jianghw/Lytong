package com.tzly.ctcyh.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.tzly.ctcyh.router.util.ActivityUtils;

/**
 * 向外提供的路由规则
 */

public abstract class LibUiRouter implements IComponentRouter {

    protected String[] HOSTS = initHostToLib();

    protected abstract String[] initHostToLib();

    @Override
    public boolean openUriBundle(Context context, String url, Bundle bundle) {
        return !(TextUtils.isEmpty(url) || context == null)
                && openUriBundle(context, Uri.parse(url), bundle);
    }

    /**
     * 实现父类方法
     */
    @Override
    public boolean openUriBundle(Context context, Uri uri, Bundle bundle) {
        if (uri == null || context == null) {
            return false;
        }
        String host = uri.getHost();
        Intent intent = new Intent();
        //当路由存在时向下执行
        if (gotoActivity(context, host, intent)) return false;
        //默认情况下登录状态向下执行
        if (excludeLoginActivity(host)) return false;

        intent.putExtras(bundle == null ? new Bundle() : bundle);
        ActivityUtils.startActivity(intent);
        return true;
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    protected abstract boolean gotoActivity(Context context, String host, Intent intent);

    /**
     * 不否需要登录逻辑
     * false -->不用登录 向下走
     *
     * @param host
     */
    protected abstract boolean excludeLoginActivity(String host);

    @Override
    public boolean verifyUri(Uri uri) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (verifySchemeToLib().equals(scheme)) {
            for (String str : HOSTS) {
                if (str.equals(host)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 子类实现
     */
    protected abstract String verifySchemeToLib();

    /**
     * 有返回的调用
     */
    @Override
    public boolean openUriForResult(Activity activity, String url, Bundle bundle, int requestCode) {
        return !(TextUtils.isEmpty(url) || activity == null)
                && openUriForResult(activity, Uri.parse(url), bundle, requestCode);
    }

    @Override
    public boolean openUriForResult(Activity activity, Uri uri, Bundle bundle, int requestCode) {
        if (uri == null || activity == null) {
            return false;
        }
        String host = uri.getHost();
        Intent intent = new Intent();
        if (gotoActivity(activity, host, intent)) return false;
        if (excludeLoginActivity(host)) return false;

        intent.putExtras(bundle == null ? new Bundle() : bundle);
        ActivityUtils.startActivityForResult(intent, requestCode);
        return true;
    }
}
