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

    void gotoCouponStatusActivity(Context context);

    /**
     * web html
     */
    void gotoWebHtmlActivity(Context context, String title, String url);

    void gotoWebHtmlActivity(Context context, String title, String url, String num, String enginenum);
    /**
     * 支付成功页面
     */
    void gotoPaySucceedActivity(Context context, String type);
}
