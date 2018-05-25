package com.zantong.mobilecttx.serviceimple;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.tzly.ctcyh.service.IMainService;
import com.zantong.mobilecttx.card.activity.ApplyCardFirstActivity;
import com.zantong.mobilecttx.huodong.activity.HundredAgreementActivity;
import com.zantong.mobilecttx.huodong.activity.HundredRuleActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.jumptools.Act;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class MainDataService implements IMainService {

    @Override
    public void gotoOrderDetailActivity(Activity context, String orderId, int couponType) {
        if (couponType == 6)//年检
            MainRouter.gotoAnnualDetailActivity(context, orderId);
        else
            MainRouter.gotoOrderDetailActivity(context, orderId);
    }

    @Override
    public void gotoOrderSucceedActivity(Activity context, String orderId, int couponType) {
        //支付成功
        if (couponType == 3) {
            MainRouter.gotoFahrschuleActivity(context, 2);
        } else if (couponType == 4) {
            MainRouter.gotoSubjectActivity(context, 3);
        } else if (couponType == 5) {
            MainRouter.gotoSparringActivity(context, 2);
        } else {//主页
            MainRouter.gotoMainActivity(context, 1);
        }
    }

    /**
     * 绑定畅通卡页面
     *
     * @param context
     */
    @Override
    public void gotoUnblockedCardActivity(Context context) {
        MainRouter.gotoUnblockedCardActivity(context);
    }

    @Override
    public void gotoMyCardActivity(Context context) {
        MainRouter.gotoMyCardActivity(context);
    }

    /**
     * 违章列表
     */
    @Override
    public void gotoViolationListActivity(Context context, String carnum, String enginenum, String carnumtype) {
        MainRouter.gotoViolationListActivity(context, carnum, enginenum, carnumtype);
    }

    @Override
    public void gotoOcrCameraActivity(Activity context) {
        MainRouter.gotoVehicleCameraActivity(context);
    }

    /**
     * 去绑定畅通卡页面
     */
    @Override
    public void loginFilenumDialog(Activity activity) {
        MainRouter.loginFilenumDialog(activity);
    }


    /**
     * 获取设备推动id
     * PushServiceFactory.getCloudPushService().getDeviceId()
     */
    @Override
    public String getPushId() {
        return PushServiceFactory.getCloudPushService().getDeviceId();
    }

    @Override
    public void gotoMainActivity(Context activity, int position) {
        MainRouter.gotoMainActivity(activity, position);
    }

    /**
     * 联系客服
     */
    @Override
    public void gotoProblemFeedbackActivity(Activity activity) {
        MainRouter.gotoProblemFeedbackActivity(activity);
    }

    /**
     * 加油地图
     */
    @Override
    public void gotoOilMapActivity(Context context) {
        MainRouter.gotoOilMapActivity(context);
    }

    /**
     * 去往活动规则页面
     */
    @Override
    public void gotoActiveActivity(Context activity, int channel) {
        MainRouter.gotoActiveActivity(activity, channel);
    }

    /**
     * html 广告页面
     */
    @Override
    public void gotoHtmlActivity(Context context, String title, String msg) {
        MainRouter.gotoWebHtmlActivity(context, title, msg);
    }

    /**
     * 去办畅通卡
     */
    @Override
    public void gotoApplyCardFirstActivity(Context activity) {
        Act.getInstance().gotoIntent(activity, ApplyCardFirstActivity.class);
    }

    @Override
    public void gotoHundredAgreementActivity(Context context) {
        Act.getInstance().gotoIntent(context, HundredAgreementActivity.class);
    }

    @Override
    public void gotoHundredRuleActivity(Context context) {
        Act.getInstance().gotoIntent(context, HundredRuleActivity.class);
    }

    @Override
    public void gotoDrivingActivity(Context context) {
        MainRouter.gotoDrivingActivity(context);
    }

    @Override
    public void gotoViolationActivity(Context context) {
        MainRouter.gotoViolationActivity(context);
    }

    @Override
    public void gotoNianjianMapActivity(Context context) {
        MainRouter.gotoInspectionMapActivity(context);
    }

    /**
     * 获取fragment 页面
     */
    @Override
    public Fragment getAdvModuleFragment() {
        return MainRouter.initAdvModuleFragment();
    }

    /**
     * 点击 统计
     */
    @Override
    public void gotoCustomerService(String url, String title, String keyId, FragmentActivity activity) {
        MainRouter.gotoCustomerService(url, title, keyId, activity);
    }

    @Override
    public void gotoByTargetPath(String url, FragmentActivity activity) {
        MainRouter.gotoByTargetPath(url, activity);
    }


}
