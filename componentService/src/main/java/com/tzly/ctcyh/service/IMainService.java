package com.tzly.ctcyh.service;

import android.app.Activity;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IMainService {

    void gotoOrderDetailActivity(Activity context, String orderId, int couponType);

    void gotoOrderSucceedActivity(Activity context, String orderId, int couponType);

    void gotoUnblockedCardActivity(Activity context);

    void gotoMyCardActivity(Activity context);

    void gotoViolationListActivity(Activity context, String carnum, String enginenum, String carnumtype);

    void gotoOcrCameraActivity(Activity context);
}
