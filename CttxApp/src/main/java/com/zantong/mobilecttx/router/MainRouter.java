package com.zantong.mobilecttx.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.service.ICargoService;
import com.tzly.ctcyh.service.IPayService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.utils.DialogMgr;
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
     * ---------------------用户模块------------------------
     */
    private static Object getUserObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(IUserService.class.getSimpleName());
    }

    /**
     * 登录页面
     */
    public static void gotoLoginActivity(Context context) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.gotoLoginActivity(context);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    public static boolean isUserLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isLogin();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return false;
        }
    }

    /**
     * 页面跳转判断是否登录用
     */
    public static boolean gotoByIsLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isUserByLogin();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return true;
        }
    }

    /**
     * 保存注册用户数据
     */
    public static void saveLoginBean(Activity activity, String user, String pwd) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.saveLoginBean(activity, user, pwd);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    /**
     * 更新驾挡编号 fileNum
     */
    public static void saveUserFilenum(String fileNum) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.saveUserFilenum(fileNum);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    public static void saveUserPortrait(String fileNum) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.saveUserPortrait(fileNum);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    public static void saveUserGetdate(String fileNum) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.saveUserGetdate(fileNum);
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    /**
     * 用户id
     */
    public static String getUserID() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserID();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getRASUserID() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
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
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserPhoenum();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getPhoneDeviceId() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getPhoneDeviceId();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getPushId() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getPushId();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserFilenum() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserFilenum();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserGetdate() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserGetdate();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserPortrait() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserPortrait();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    public static String getUserNickname() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserNickname();
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
            return "";
        }
    }

    /**
     * 推送 id
     */
    public static void savePushId(String id) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.savePushId(id);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    /**
     * 退出登录
     */
    public static void cleanUserLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.cleanUserLogin();
        } else {
            //注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
        }
    }

    /**
     * 去绑定畅通卡页面
     */
    public static void loginFilenumDialog(final Activity activity) {
        inputManager(activity);
        boolean show = SPUtils.getInstance(SPUtils.FILENAME).getBoolean(SPUtils.USER_LOGIN_DIALOG, true);
        if (show && TextUtils.isEmpty(getUserFilenum())) {
            new DialogMgr(activity,
                    "登录成功", "畅通车友会欢迎您，赶快去注册您的牡丹卡吧！", "添加畅通卡", "继续",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoUnblockedCardActivity(activity);
                            if (activity != null) gotoMainActivity(activity, 0);
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (activity != null) gotoMainActivity(activity, 0);
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SPUtils.getInstance(SPUtils.FILENAME).put(SPUtils.USER_LOGIN_DIALOG, false);
                            if (activity != null) activity.finish();
                        }
                    });
        } else {
            gotoMainActivity(activity, 0);
        }
    }

    private static void inputManager(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void gotoRegisterActivity(Activity activity) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.register_host,
                bundle);
    }

    public static void gotoResetActivity(Activity activity) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.reset_host,
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
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        Object object = serviceRouter.getService(ICargoService.class.getSimpleName());
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoRechargeActivity(context);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.cargo.like.CargoAppLike");
        }
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
     * 消息详情页面
     */
    public static void gotoMegDetailActivity(Context context, String title, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.meg_title_extra, title);
        bundle.putString(MainGlobal.putExtra.meg_id_extra, id);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.meg_detail_host,
                bundle);
    }

    public static void gotoMegDetailActivity(Activity activity, String title, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.meg_title_extra, title);
        bundle.putString(MainGlobal.putExtra.meg_id_extra, id);
        UiRouter.getInstance().openUriForResult(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.meg_detail_host,
                bundle, MainGlobal.requestCode.meg_detail_del);
    }

    /**
     * 违章查询页面
     */
    public static void gotoViolationActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.violation_query_host,
                bundle);
    }

    public static void gotoViolationActivity(Activity activity, Bundle bundle) {
        UiRouter.getInstance().openUriForResult(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.violation_query_host,
                bundle, MainGlobal.requestCode.violation_query_bean);
    }

    /**
     * 改绑页面
     */
    public static void gotoSetPayCarActivity(Activity activity) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriForResult(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.set_pay_car_host,
                bundle, MainGlobal.requestCode.set_pay_car);
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

    public static void gotoViolationListActivity(Activity context,
                                                 String carnum, String enginenum, String carnumtype) {
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

    private static Object getPayObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(IPayService.class.getSimpleName());
    }

    /**
     * 优惠券列表
     */
    public static void gotoCouponStatusActivity(Context context) {
        Object object = getPayObject();
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoCouponStatusActivity(context);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.pay.like.PayAppLike");
        }
    }

    /**
     * 支付页面页面 其他模块
     */
    public static void gotoPayTypeActivity(Activity context, String orderId) {
        Object object = getPayObject();
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoPayTypeActivity(context, orderId);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.pay.like.PayAppLike");
        }
    }

    /**
     * 银行卡支付页面
     */
    public static void gotoHtmlActivity(Activity activity, String title, String content,
                                        String orderId, int type) {
        Object object = getPayObject();
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoHtmlActivity(activity, title, content, orderId, type);
        } else {//注册机开始工作
            ServiceRouter.registerComponent("com.tzly.ctcyh.pay.like.PayAppLike");
        }
    }

    /**
     * html 广告 优惠等页面
     */
    public static void gotoHtmlActivity(Context activity, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.browser_title_extra, title);
        bundle.putString(MainGlobal.putExtra.browser_url_extra, url);
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.html_self_host,
                bundle);
    }

    public static void gotoPayHtmlActivity(Context activity, String title, String url, String num) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.browser_title_extra, title);
        bundle.putString(MainGlobal.putExtra.browser_url_extra, url);
        bundle.putString(MainGlobal.putExtra.violation_num_extra, num);
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.html_pay_host,
                bundle);
    }

    /**
     * 问题页面
     */
    public static void gotoProblemFeedbackActivity(Activity activity) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.problem_feed_host,
                bundle);
    }

    public static void gotoBaiduMapParentActivity(Activity context) {
        Bundle bundle = new Bundle();
        bundle.putInt(JxGlobal.putExtra.map_type_extra, JxGlobal.MapType.annual_oil_map);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.oil_map_host,
                bundle);
    }
}
