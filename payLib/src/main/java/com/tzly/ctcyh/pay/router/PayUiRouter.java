package com.tzly.ctcyh.pay.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.tzly.ctcyh.pay.EntryActivity;
import com.tzly.ctcyh.router.IComponentRouter;

/**
 * 向外提供的路由规则
 */

public class PayUiRouter implements IComponentRouter {

    private static final String SCHEME_PAY = "scheme_pay";
    private static final String HOST_PAY = "host_pay";

    private static String[] HOSTS = new String[]{HOST_PAY};

    /**
     * 单例
     */
    public static PayUiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final PayUiRouter INSTANCE = new PayUiRouter();
    }

    @Override
    public boolean openUri(Context context, String url, Bundle bundle) {
        return TextUtils.isEmpty(url) || context == null || openUri(context, Uri.parse(url), bundle);
    }

    /**
     * 实现父类方法
     */
    @Override
    public boolean openUri(Context context, Uri uri, Bundle bundle) {
        if (uri == null || context == null) {
            return true;
        }
        String host = uri.getHost();
        if (HOST_PAY.equals(host)) {
            Intent intent = new Intent(context, EntryActivity.class);
            intent.putExtras(bundle == null ? new Bundle() : bundle);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyUri(Uri uri) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (SCHEME_PAY.equals(scheme)) {
            for (String str : HOSTS) {
                if (str.equals(host)) {
                    return true;
                }
            }
        }
        return false;
    }
}
