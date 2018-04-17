package com.zantong.mobilecttx.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.UiRouter;
import com.tzly.ctcyh.router.custom.dialog.DialogMgr;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.service.ICargoService;
import com.tzly.ctcyh.service.IPayService;
import com.tzly.ctcyh.service.IUserService;
import com.tzly.ctcyh.service.RouterGlobal;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.home.bean.StartPicBean;
import com.zantong.mobilecttx.home_v.AdvActiveFragment;
import com.zantong.mobilecttx.home_v.RouterUtils;
import com.zantong.mobilecttx.push_v.AliPushExtBean;
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
            registerUser();
        }
    }

    private static void registerUser() {
        ServiceRouter.registerComponent(ServiceRouter.USER_LIKE);
    }

    public static boolean isUserLogin() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.isLogin();
        } else {//注册机开始工作
            registerUser();
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
            registerUser();
            return true;
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
            registerUser();
        }
    }

    public static void saveUserPortrait(String fileNum) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.saveUserPortrait(fileNum);
        } else {
            //注册机开始工作
            registerUser();
        }
    }

    public static void saveUserGetdate(String fileNum) {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            service.saveUserGetdate(fileNum);
        } else {
            //注册机开始工作
            registerUser();
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
            registerUser();
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
            registerUser();
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
            registerUser();
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
            registerUser();
            return "";
        }
    }

    public static String getPushId() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getPushId();
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    public static String getUserFilenum() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserFilenum();
        } else {//注册机开始工作
            registerUser();
            return "";
        }
    }

    public static String getUserGetdate() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserGetdate();
        } else {//注册机开始工作
            registerUser();
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
            registerUser();
            return "";
        }
    }

    public static String getUserNickname() {
        Object object = getUserObject();
        if (object != null && object instanceof IUserService) {
            IUserService service = (IUserService) object;
            return service.getUserNickname();
        } else {//注册机开始工作
            registerUser();
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
            registerUser();
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
        } else {//注册机开始工作
            registerUser();
        }
    }

    /**
     * 去绑定畅通卡页面
     */
    public static void loginFilenumDialog(final Activity activity) {

        boolean show = SPUtils.instance().getBoolean(SPUtils.USER_LOGIN_DIALOG, true);
        if (show && TextUtils.isEmpty(getUserFilenum())) {
            new DialogMgr(activity,
                    "登录成功", "欢迎您，赶快去注册您的牡丹卡吧！", "添加畅通卡", "继续",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (activity != null) gotoMainActivity(activity, 0);
                            if (activity != null) gotoUnblockedCardActivity(activity);
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
                            SPUtils.instance().put(SPUtils.USER_LOGIN_DIALOG, false);
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
     * 调动活动的主页
     */
    public static void gotoMainActivity(Context context, int postion, String channel, String date) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.putExtra.home_position_extra, postion);
        bundle.putString(RouterGlobal.putExtra.channel_active, channel);
        bundle.putString(RouterGlobal.putExtra.channel_register_date, date);

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
     * 年检地图页面
     */
    public static void gotoInspectionMapActivity(Context context) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.putExtra.map_type_extra, MainGlobal.MapType.annual_inspection_map);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.baidu_map_host,
                bundle);
    }

    /**
     * 加油地图
     */
    public static void gotoOilMapActivity(Context context) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.putExtra.map_type_extra, MainGlobal.MapType.annual_oil_map);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.baidu_map_host,
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
    public static void gotoFahrschuleActivity(Context context, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.Host.fahrschule_host, position);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.fahrschule_host,
                bundle);
    }

    /**
     * 陪练页面
     */
    public static void gotoSparringActivity(Context context, int position) {
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
     * 违章列表页面
     */
    public static void gotoViolationListActivity(Context context,
                                                 String carnum, String enginenum, String carnumtype) {
        ViolationDTO dto = new ViolationDTO();
        dto.setCarnum(carnum);
        dto.setEnginenum(enginenum);
        dto.setCarnumtype(carnumtype);

        Bundle bundle = new Bundle();
        bundle.putSerializable("params", dto);

        gotoViolationListActivity(context, bundle);
    }

    /**
     * 违章列表页面
     */
    public static void gotoViolationListActivity(Context activity, Bundle bundle) {
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.violation_list_host,
                bundle);
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
    public static void gotoUnblockedCardActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.unblocked_card_host,
                bundle);
    }

    public static void gotoMyCardActivity(Context context) {
        Bundle bundle = new Bundle();
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.my_card_host,
                bundle);
    }

    /**
     * 行驶证扫描
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
     * 统一 基础 html 广告 优惠等页面
     */
    public static void gotoWebHtmlActivity(Context context, String title, String url) {
        Object object = getPayObject();
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoWebHtmlActivity(context, title, url);
        } else {//注册机开始工作
            registerPay();
        }
    }

    /**
     * 违章支付页面
     */
    public static void gotoWebHtmlActivity(Context context, String title, String url,
                                           String num, String enginenum) {
        Object object = getPayObject();
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoWebHtmlActivity(context, title, url, num, enginenum);
        } else {//注册机开始工作
            registerPay();
        }
    }

    private static void registerPay() {
        ServiceRouter.registerComponent(ServiceRouter.PAY_LIKE);
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

    /**
     * 违章查分输入面
     * 2--
     * 1--
     */
    public static void gotoLicenseGradeActivity(Context context, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainGlobal.putExtra.license_position_extra, position);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.license_grade_host,
                bundle);
    }

    /**
     * 缴费查询
     */
    public static void gotoPaymentActivity(Context context, String bean) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.license_bean_extra, bean);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.pay_ment_host,
                bundle);
    }

    /**
     * 违章查分页面
     */
    public static void gotoLicenseDetailActivity(Context context, String gson) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.license_bean_extra, gson);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.license_detail_host,
                bundle);
    }

    /**
     * 富文本
     */
    public static void gotoRichTextActivity(Context context, String gson) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.home_rich_extra, gson);
        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.home_rich_host,
                bundle);
    }

    /**
     * 加油页面
     */
    public static void gotoRechargeActivity(Context context) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoRechargeActivity(context);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    private static void registerCargo() {
        ServiceRouter.registerComponent(ServiceRouter.CARGO_LIKE);
    }

    /**
     * 驾驶证
     */
    public static void gotoDrivingCameraActivity(Activity activity) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoDrivingCameraActivity(activity);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    /**
     * 行驶证
     */
    public static void gotoVehicleCameraActivity(Activity activity) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoVehicleCameraActivity(activity);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    private static Object getCargoObject() {
        ServiceRouter serviceRouter = ServiceRouter.getInstance();
        return serviceRouter.getService(ICargoService.class.getSimpleName());
    }

    /**
     * 去往活动规则页面
     * 1--违章列表结果
     * 2--支付
     * 3--绑卡成功
     */
    public static void gotoActiveActivity(Context context, int channel) {
        gotoActiveActivity(context, channel, "");
    }

    /**
     * 优惠页面 出现活动内容
     */
    public static void gotoActiveActivity(Context context, int channel, String date) {
        gotoMainActivity(context, 1, String.valueOf(channel), date);
    }

    /**
     * 97 加油
     */
    public static void gotoDiscountOilActivity(Context context) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoDiscountOilActivity(context);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    /**
     * 97申请办卡
     */
    public static void gotoBidOilActivity(Context context) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoBidOilActivity(context);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    /**
     * 97 加油 申请办卡
     */
    public static void gotoFoldOilActivity(Context context) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoFoldOilActivity(context);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    /**
     * 加油统一入口
     */
    public static void gotoOilEnterActivity(Context context) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoOilEnterActivity(context);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    /**
     * 驾照查分
     */
    public static void gotoLicenseCargoActivity(Context context) {
        Object object = getCargoObject();
        if (object != null && object instanceof ICargoService) {
            ICargoService service = (ICargoService) object;
            service.gotoLicenseCargoActivity(context);
        } else {//注册机开始工作
            registerCargo();
        }
    }

    /**
     * 支付成功页面
     */
    public static void gotoPaySucceedActivity(Context context, String type) {
        Object object = getPayObject();
        if (object != null && object instanceof IPayService) {
            IPayService service = (IPayService) object;
            service.gotoPaySucceedActivity(context, type);
        } else {//注册机开始工作
            registerPay();
        }
    }

    /**
     * 启动页面
     */
    public static void gotoSplashActivity(Context context, AliPushExtBean extBean) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.splash_type_extra, extBean.getType());
        bundle.putString(MainGlobal.putExtra.splash_id_extra, extBean.getId());
        bundle.putString(MainGlobal.putExtra.splash_url_extra, extBean.getUrl());

        UiRouter.getInstance().openUriBundle(context,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.splash_activity_host,
                bundle);
    }

    /**
     * 退订单
     */
    public static void gotoOrderRefundActivity(Activity activity, String mOrderId) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.web_order_id_extra, mOrderId);
        UiRouter.getInstance().openUriBundle(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.order_refund_host,
                bundle);
    }

    /**
     * 修改订单
     */
    public static void gotoAmendOrderActivity(Activity activity, String mOrderId) {
        Bundle bundle = new Bundle();
        bundle.putString(MainGlobal.putExtra.web_order_id_extra, mOrderId);
        UiRouter.getInstance().openUriForResult(activity,
                RouterGlobal.Scheme.main_scheme + "://" + RouterGlobal.Host.order_amend_host,
                bundle,
                MainGlobal.requestCode.order_detail_amend);
    }

    /**
     * 获取 fragment 页面
     */
    public static Fragment getAdvActiveFragment() {
        return AdvActiveFragment.newInstance();
    }

    /**
     * 点击 统计
     */
    public static void gotoCustomerService(String url, String title, String keyId, FragmentActivity activity) {
        RouterUtils.gotoByStatistId(url, title, keyId, activity);
    }
}
