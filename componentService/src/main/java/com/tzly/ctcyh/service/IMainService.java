package com.tzly.ctcyh.service;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

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

    void gotoMainActivity(Context activity, int position);

    void gotoProblemFeedbackActivity(Activity activity);

    void gotoOilMapActivity(Context context);

    void gotoActiveActivity(Context activity, int channel);

    void gotoHtmlActivity(Context activity, String title, String msg);

    void gotoApplyCardFirstActivity(Context activity);

    void gotoHundredAgreementActivity(Context context);

    void gotoHundredRuleActivity(Context context);

    void gotoDrivingActivity(Context context);

    void gotoViolationActivity(Context context);

    void gotoNianjianMapActivity(Context context);

    Fragment getAdvActiveFragment();

    /**
     * 点击 统计
     */
    void gotoCustomerService(String url, String title, String keyId, FragmentActivity activity);

    void gotoByTargetPath(String url, FragmentActivity activity);
}
