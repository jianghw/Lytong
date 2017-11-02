package com.zantong.mobilecttx.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.service.IPayService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

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
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
             service.gotoLoginActivity(context);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }

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
    public static void gotoSubjectActivity(Context context, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.Host.subject_host, position);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.subject_host,
                bundle);
    }

    /**
     * 驾校报名页面
     */
    public static void gotoFahrschuleActivity(Activity context, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.Host.fahrschule_host, position);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.fahrschule_host,
                bundle);
    }

    /**
     * 陪练页面
     */
    public static void gotoSparringActivity(Activity context, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.Host.sparring_host, position);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.sparring_host,
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

    /**
     * 订单详情页面
     */
    public static void gotoOrderDetailActivity(Context context, String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.web_order_id_extra, orderId);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.order_detail_host,
                bundle);
    }

    public static void gotoAnnualDetailActivity(Context context, String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.web_order_id_extra, orderId);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.annual_detail_host,
                bundle);
    }

    /**
     * 用户id
     *
     * @param isNeedLogin
     */
    public static String getUserID(boolean isNeedLogin) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserID(isNeedLogin);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getRASUserID() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getRASUserID();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    /**
     * 手机号码
     */
    public static String getUserPhoenum() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserPhoenum();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserFilenum() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserFilenum();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserGetdate() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserGetdate();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static boolean isUserLogin() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.isLogin();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return false;
        }
    }

    public static String getUserPortrait() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserPortrait();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserNickname() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IUserService.class.getSimpleName());
        if (object != null) {
            IUserService service = (IUserService) object;
            return service.getUserNickname();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    /**
     * 绑定畅通卡页面
     */
    public static void gotoUnblockedCardActivity(Activity context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.unblocked_card_host,
                bundle);
    }

    public static void gotoMyCardActivity(Activity context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.my_card_host,
                bundle);
    }

    public static void gotoViolationListActivity(Activity context, String carnum, String enginenum, String carnumtype) {
        LoginData.getInstance().mHashMap.put("IllegalViolationName", carnum);
        LoginData.getInstance().mHashMap.put("carnum", carnum);
        LoginData.getInstance().mHashMap.put("enginenum", enginenum);
        LoginData.getInstance().mHashMap.put("carnumtype", carnumtype);

        ViolationDTO dto = new ViolationDTO();
        dto.setCarnum(RSAUtils.strByEncryption(carnum, true));
        dto.setEnginenum(RSAUtils.strByEncryption(enginenum, true));
        dto.setCarnumtype(carnumtype);

        Bundle bundle = new Bundle();
        bundle.putSerializable("params", dto);

        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.violation_list_host,
                bundle);
    }

    /**
     * 扫描
     */
    public static void gotoOcrCameraActivity(Activity context) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.putExtra.ocr_camera_extra, 0);

        UiRouter.getInstance().openUriForResult(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.ocr_camera_host,
                bundle, MainGlobal.requestCode.violation_query_camera);
    }

    /**
     * 银行卡支付页面
     */
    public static void gotoHtmlActivity(Activity browserHtmlActivity, String title, String content, String orderId, int type) {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(IPayService.class.getSimpleName());
        if (object != null) {
            IPayService service = (IPayService) object;
            service.gotoHtmlActivity(browserHtmlActivity, title, content, orderId, type);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.pay.like.PayAppLike");

        }
    }
}
