package com.tzly.ctcyh.router;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

/**
 * 组件间的路由规则
 */

public interface IComponentRouter {

    /**
     * 打开一个链接
     *
     * @param url    目标url可以是http 或者 自定义scheme
     * @param bundle 打开目录activity时要传入的参数。建议只传基本类型参数。
     * @return 是否正常打开
     */
    boolean openUriBundle(Context context, String url, Bundle bundle);

    boolean openUriBundle(Context context, Uri uri, Bundle bundle);

    boolean verifyUri(Uri uri);

    boolean openUriForResult(Activity activity, String url, Bundle bundle,int requestCode);

    boolean openUriForResult(Activity activity, Uri uri, Bundle bundle,int requestCode);

}
