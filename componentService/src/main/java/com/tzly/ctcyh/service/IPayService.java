package com.tzly.ctcyh.service;

import android.app.Activity;
import android.content.Context;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IPayService {

    void gotoPayTypeActivity(Activity context, String orderId);

    /**
     * 去支付吧
     */
    void gotoPayHtmlActivity(Activity context, String title, String url,
                             String extraOrderId, int payType, String mChannel);

    void gotoHtmlActivity(Activity context, String title, String url);

    void gotoCouponStatusActivity(Context context);

    /**
     * web html
     */
    void gotoWebHtmlActivity(Activity activity, String title, String url);

    void gotoWebHtmlActivity(Context context, String title, String url);
}
