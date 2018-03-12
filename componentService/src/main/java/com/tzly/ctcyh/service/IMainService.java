package com.tzly.ctcyh.service;

import android.app.Activity;
import android.content.Context;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IMainService {

    void gotoOrderDetailActivity(Activity context, String orderId, int couponType);

    void gotoOrderSucceedActivity(Activity context, String orderId, int couponType);

    void gotoUnblockedCardActivity(Context context);

    void gotoMyCardActivity(Context context);

    void gotoViolationListActivity(Context context, String carnum, String enginenum, String carnumtype);

    void gotoOcrCameraActivity(Activity context);

    void loginFilenumDialog(Activity activity);

    String getPushId();

    void gotoMainActivity(Context activity, int i);

    void gotoProblemFeedbackActivity(Activity activity);

    void gotoBaiduMapParentActivity(Activity context);

    void gotoActiveActivity(Context activity, int i);

    void gotoHtmlActivity(Context activity, String title, String msg);

    void gotoApplyCardFirstActivity(Context activity);

    void gotoHundredAgreementActivity(Context context);

    void gotoHundredRuleActivity(Context context);

    void gotoDrivingActivity(Context context);

    void gotoViolationActivity(Context context);

    void gotoNianjianMapActivity(Context context);
}
