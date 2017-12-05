package com.zantong.mobilecttx.router;

import android.content.Context;
import android.content.Intent;

import com.tzly.ctcyh.router.IComponentRouter;
import com.tzly.ctcyh.router.LibUiRouter;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.browser.BrowserHtmlActivity;
import com.zantong.mobilecttx.browser.PayHtmlActivity;
import com.zantong.mobilecttx.car.activity.SetPayCarActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.card.activity.UnblockedCardActivity;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.activity.DrivingActivity;
import com.zantong.mobilecttx.fahrschule_v.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule_v.SparringActivity;
import com.zantong.mobilecttx.fahrschule_v.SubjectActivity;
import com.zantong.mobilecttx.guide_v.GuideCTActivity;
import com.zantong.mobilecttx.home.activity.CaptureActivity;
import com.zantong.mobilecttx.home_v.HomeMainActivity;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.msg_v.MegDetailActivity;
import com.zantong.mobilecttx.order_v.AnnualDetailActivity;
import com.zantong.mobilecttx.order_v.OrderDetailActivity;
import com.zantong.mobilecttx.msg_v.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.activity.RegisterActivity;
import com.zantong.mobilecttx.user.activity.ResetActivity;
import com.zantong.mobilecttx.violation_v.ViolationActivity;
import com.zantong.mobilecttx.violation_v.ViolationListActivity;

/**
 * 向外提供的路由规则
 */

public class MainUiRouter extends LibUiRouter implements IComponentRouter {

    /**
     * 单例
     */
    public static MainUiRouter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MainUiRouter INSTANCE = new MainUiRouter();
    }

    @Override
    protected String[] initHostToLib() {
        return new String[]{
                RouterGlobal.Host.home_host,
                RouterGlobal.Host.guide_host,
                RouterGlobal.Host.map_host,
                RouterGlobal.Host.driving_host,
                RouterGlobal.Host.meg_type_host,
                RouterGlobal.Host.capture_host,
                RouterGlobal.Host.subject_host,
                RouterGlobal.Host.order_detail_host,
                RouterGlobal.Host.annual_detail_host,
                RouterGlobal.Host.fahrschule_host,
                RouterGlobal.Host.sparring_host,
                RouterGlobal.Host.unblocked_card_host,
                RouterGlobal.Host.violation_list_host,
                RouterGlobal.Host.ocr_camera_host,
                RouterGlobal.Host.register_host,
                RouterGlobal.Host.reset_host,
                RouterGlobal.Host.meg_detail_host,
                RouterGlobal.Host.html_self_host,
                RouterGlobal.Host.html_pay_host,
                RouterGlobal.Host.violation_query_host,
                RouterGlobal.Host.set_pay_car_host,
                RouterGlobal.Host.my_card_host,
                RouterGlobal.Host.problem_feed_host,
                RouterGlobal.Host.oil_map_host
        };
    }

    /**
     * 统一的路由规则，需要定义的在这里操作
     */
    @Override
    protected boolean gotoActivity(Context context, String host, Intent intent) {
        if (RouterGlobal.Host.home_host.equals(host)) {
            intent.setClass(context, HomeMainActivity.class);
        } else if (RouterGlobal.Host.guide_host.equals(host)) {
            intent.setClass(context, GuideCTActivity.class);
        } else if (RouterGlobal.Host.map_host.equals(host)) {
            intent.setClass(context, BaiduMapParentActivity.class);
        } else if (RouterGlobal.Host.driving_host.equals(host)) {
            intent.setClass(context, DrivingActivity.class);
        } else if (RouterGlobal.Host.meg_type_host.equals(host)) {
            intent.setClass(context, MegTypeActivity.class);
        } else if (RouterGlobal.Host.capture_host.equals(host)) {
            intent.setClass(context, CaptureActivity.class);
        } else if (RouterGlobal.Host.subject_host.equals(host)) {
            intent.setClass(context, SubjectActivity.class);
        } else if (RouterGlobal.Host.order_detail_host.equals(host)) {
            intent.setClass(context, OrderDetailActivity.class);
        } else if (RouterGlobal.Host.annual_detail_host.equals(host)) {
            intent.setClass(context, AnnualDetailActivity.class);
        } else if (RouterGlobal.Host.fahrschule_host.equals(host)) {
            intent.setClass(context, FahrschuleActivity.class);
        } else if (RouterGlobal.Host.sparring_host.equals(host)) {
            intent.setClass(context, SparringActivity.class);
        } else if (RouterGlobal.Host.unblocked_card_host.equals(host)) {
            intent.setClass(context, UnblockedCardActivity.class);
        } else if (RouterGlobal.Host.my_card_host.equals(host)) {
            intent.setClass(context, MyCardActivity.class);
        } else if (RouterGlobal.Host.violation_list_host.equals(host)) {
            intent.setClass(context, ViolationListActivity.class);
        } else if (RouterGlobal.Host.ocr_camera_host.equals(host)) {
            intent.setClass(context, OcrCameraActivity.class);
        } else if (RouterGlobal.Host.register_host.equals(host)) {
            intent.setClass(context, RegisterActivity.class);
        } else if (RouterGlobal.Host.reset_host.equals(host)) {
            intent.setClass(context, ResetActivity.class);
        } else if (RouterGlobal.Host.meg_detail_host.equals(host)) {
            intent.setClass(context, MegDetailActivity.class);
        } else if (RouterGlobal.Host.html_pay_host.equals(host)) {
            intent.setClass(context, PayHtmlActivity.class);
        } else if (RouterGlobal.Host.html_self_host.equals(host)) {
            intent.setAction("com.zantong.mobilecttx.browser.BrowserHtmlActivity");
            intent.setClass(context, BrowserHtmlActivity.class);
        } else if (RouterGlobal.Host.violation_query_host.equals(host)) {
            intent.setClass(context, ViolationActivity.class);
        } else if (RouterGlobal.Host.set_pay_car_host.equals(host)) {
            intent.setClass(context, SetPayCarActivity.class);
        } else if (RouterGlobal.Host.problem_feed_host.equals(host)) {
            intent.setClass(context, ProblemFeedbackActivity.class);
        } else if (RouterGlobal.Host.oil_map_host.equals(host)) {
            intent.setClass(context, BaiduMapParentActivity.class);
        } else {
            return true;
        }
        return false;
    }

    /**
     * 不用登录逻辑
     */
    @Override
    protected boolean excludeLoginActivity(String host) {
        //可添加不需要登录业务
        return !RouterGlobal.Host.guide_host.equals(host)
                && !RouterGlobal.Host.home_host.equals(host)
                && !RouterGlobal.Host.capture_host.equals(host)
                && !RouterGlobal.Host.violation_list_host.equals(host)
                && !RouterGlobal.Host.ocr_camera_host.equals(host)
                && !RouterGlobal.Host.register_host.equals(host)
                && !RouterGlobal.Host.reset_host.equals(host)
                && !RouterGlobal.Host.violation_query_host.equals(host)
                && !MainRouter.gotoByIsLogin();
    }

    @Override
    protected String verifySchemeToLib() {
        return RouterGlobal.Scheme.main_scheme;
    }
}
