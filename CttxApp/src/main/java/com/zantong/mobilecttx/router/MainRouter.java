package com.zantong.mobilecttx.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.service.IPayService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.home.bean.StartPicBean;

import java.util.ArrayList;

import static com.zantong.mobilecttx.guide_v.GuideCTActivity.GUIDE_PIC;

/**
 * Created by jianghw on 2017/10/31.
 * Description:
 * Update by:
 * Update day:
 */

public final class MainRouter {
    /**
     * 登录页面
     */
    public static void gotoLoginActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.user_scheme + "://" + RouterGlobal.Host.login_host,
                bundle);
    }

    /**
     * 主页面
     */
    public static void gotoMainActivity(Context context, int postion) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.putExtra.home_position_extra, postion);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.home_host,
                bundle);
    }

    /**
     * 引导页
     */
    public static void gotoGuideActivity(Context context, ArrayList<StartPicBean> list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GUIDE_PIC, list);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.guide_host,
                bundle);
    }

    /**
     * 地图页面
     */
    public static void gotoMapActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.map_host,
                bundle);
    }

    /**
     * 消息页面
     */
    public static void gotoMegTypeActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.meg_type_host,
                bundle);
    }

    /**
     * 扫描页面
     */
    public static void gotoCaptureActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.capture_host,
                bundle);
    }

    /**
     * 代驾页面
     */
    public static void gotoDrivingActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.driving_host,
                bundle);
    }

    /**
     * 加油页面
     */
    public static void gotoRechargeActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.recharge_host,
                bundle);
    }

    /**
     * 科目强化
     */
    public static void gotoSubjectActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.subject_host,
                bundle);
    }

    /**
     * 支付页面页面 其他模块
     */
    public static void gotoPayTypeActivity(Activity context, String orderId) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        if (serviceRouter.getService(IPayService.class.getSimpleName()) != null) {
            IPayService service = (IPayService) serviceRouter.getService(IPayService.class.getSimpleName());
            service.gotoPayTypeActivity(context, orderId);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.pay.like.PayAppLike");

        }
    }
}
