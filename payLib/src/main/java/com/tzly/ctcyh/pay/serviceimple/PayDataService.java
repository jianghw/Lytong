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
    public void gotoCouponStatusActivity(Context context) {
        PayRouter.gotoCouponStatusActivity(context);
    }

    /**
     * web html
     */
    @Override
    public void gotoWebHtmlActivity(Context context, String title, String url) {
        PayRouter.gotoWebHtmlActivity(context, title, url);
    }

    /**
     * 银行支付
     */
    @Override
    public void gotoWebHtmlActivity(Context context, String title, String url, String num, String enginenum) {
        PayRouter.gotoWebHtmlActivity(context, title, url, num, enginenum);
    }

    /**
     * 支付成功页面
     */
    @Override
    public void gotoPaySucceedActivity(Context context, String type) {
        PayRouter.gotoPaySucceedActivity(context, type);
    }

}
