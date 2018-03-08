package com.zantong.mobilecttx.serviceimple;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.tzly.ctcyh.service.IMainService;
import com.zantong.mobilecttx.card.activity.ApplyCardFirstActivity;
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
     */
    @Override
    public void gotoUnblockedCardActivity(Activity context) {
        MainRouter.gotoUnblockedCardActivity(context);
    }

    @Override
    public void gotoMyCardActivity(Activity context) {
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
    public void gotoMainActivity(Context activity, int i) {
        MainRouter.gotoMainActivity(activity, i);
    }

    @Override
    public void gotoProblemFeedbackActivity(Activity activity) {
        MainRouter.gotoProblemFeedbackActivity(activity);
    }

    /**
     * 加油地图
     */
    @Override
    public void gotoBaiduMapParentActivity(Activity context) {
        MainRouter.gotoBaiduMapParentActivity(context);
    }

    /**
     * 去往活动规则页面
     */
    @Override
    public void gotoActiveActivity(Context activity, int i) {
        MainRouter.gotoActiveActivity(activity, i);
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


}