package com.tzly.ctcyh.pay.serviceimple;

import android.app.Activity;
import android.content.Context;

import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.service.IPayService;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class PayDataService implements IPayService {

    @Override
    public void gotoPayTypeActivity(Activity context, String orderId) {
        PayRouter.gotoPayTypeActivity(context, orderId);
    }

    @Override
    public void gotoHtmlActivity(Activity context, String title, String url, String extraOrderId, int payType) {
        PayRouter.gotoHtmlActivity(context, title, url, extraOrderId, payType);
    }

    @Override
    public void gotoHtmlActivity(Activity context, String title, String url) {
        PayRouter.gotoHtmlActivity(context, title, url);
    }

    @Override
    public void gotoCouponStatusActivity(Context context) {
        PayRouter.gotoCouponStatusActivity(context);
    }
}
