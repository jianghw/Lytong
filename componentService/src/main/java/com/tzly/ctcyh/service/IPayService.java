package com.tzly.ctcyh.service;

import android.app.Activity;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IPayService {

    void gotoPayTypeActivity(Activity context, String orderId);

    void gotoHtmlActivity(Activity context, String title, String url, String extraOrderId, int payType);

    void gotoHtmlActivity(Activity context, String title, String url);

}
