package com.tzly.ctcyh.pay.serviceimple;

import android.app.Activity;

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
}
