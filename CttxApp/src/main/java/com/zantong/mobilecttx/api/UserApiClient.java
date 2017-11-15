package com.zantong.mobilecttx.api;

import android.content.Context;

import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.card.bean.BindCardResult;
import com.zantong.mobilecttx.card.dto.BindCardDTO;
import com.zantong.mobilecttx.home.bean.VersionResponse;
import com.zantong.mobilecttx.home.dto.VersionDTO;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.CheckOrderResult;
import com.zantong.mobilecttx.user.bean.LoginResult;
import com.zantong.mobilecttx.user.bean.OrderResult;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.bean.VcodeResult;
import com.zantong.mobilecttx.user.dto.ChangePwdDTO;
import com.zantong.mobilecttx.user.dto.CheckOrderDTO;
import com.zantong.mobilecttx.user.dto.FeedbackDTO;
import com.zantong.mobilecttx.user.dto.InsOrderDTO;
import com.zantong.mobilecttx.user.dto.JiaoYiDaiMaDTO;
import com.zantong.mobilecttx.user.dto.LoginDTO;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.user.dto.OrderDTO;
import com.zantong.mobilecttx.user.dto.PersonInfoDTO;
import com.zantong.mobilecttx.user.dto.RegisterDTO;
import com.zantong.mobilecttx.user.dto.ShareDTO;
import com.zantong.mobilecttx.user.dto.UpdateUserHeadImgDTO;
import com.zantong.mobilecttx.user.dto.VcodeDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.weizhang.bean.PayHistoryResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.PayHistoryDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import com.tzly.ctcyh.router.bean.BankResponse;


/**
 * 我的API
 *
 * @author Sandy
 *         create at 16/6/6 上午10:02
 */
public class UserApiClient extends BaseApiClient {


    /**
     * 请求头dto封装
     *
     * @author Sandy
     * create at 16/6/8 上午10:30
     */
    public static RequestHeadDTO getBean(String str) {
        RequestHeadDTO dto = new RequestHeadDTO();
        dto.setConsumerId("04");
        dto.setTransServiceCode(str);
        dto.setRequestDate(DateUtils.getDate());
        dto.setRequestTime(DateUtils.getTime());
        dto.setConsumerSeqNo(StringUtils.getRandomStr());
        dto.setDvcToken(Tools.getIMEI());
        return dto;
    }

    /**
     * 登录
     *
     * @author Sandy
     * create at 16/6/6 下午2:00
     */
    public static void login(Context context, LoginDTO params, CallBack<LoginResult> callback) {
        AsyncCallBack<LoginResult> asyncCallBack = new AsyncCallBack<LoginResult>(
                context, callback, LoginResult.class);
        //        private String phoenum; //手机号
        //        private String captcha;//验证码
        //        private String onlyflag;//唯一标示
        //        private String devicetoken;//设备标识号
        //        private String pushswitch;//推送开关
        //        private String pushmode;//推送方式
        //        private String chkflg;//验证标志
        params.setOnlyflag("1dsgasdags");
        params.setCaptcha("673589");
        params.setDevicetoken("387fhjdkg0jt4");
        params.setPushswitch("0");
        params.setPushmode("2");
        params.setChkflg("1");
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u001.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 注册
     */
    public static void register(Context context, RegisterDTO params, CallBack<LoginResult> callback) {
        AsyncCallBack<LoginResult> asyncCallBack = new AsyncCallBack<LoginResult>(
                context, callback, LoginResult.class);

        params.setDevicetoken(MainRouter.getPhoneDeviceId());
        params.setPushswitch("0");
        params.setPushmode("2");
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u001.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 重置密码的获取验证码
     *
     * @author Sandy
     * create at 16/6/23 下午4:25
     */
    public static void getVerifyCode2(Context context, VcodeDTO params, CallBack<VcodeResult> callback) {
        AsyncCallBack<VcodeResult> asyncCallBack = new AsyncCallBack<VcodeResult>(
                context, callback, VcodeResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u015.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 获取验证码
     *
     * @author Sandy
     * create at 16/6/23 下午4:25
     */
    public static void getVerifyCode(Context context, VcodeDTO params, CallBack<VcodeResult> callback) {
        AsyncCallBack<VcodeResult> asyncCallBack = new AsyncCallBack<VcodeResult>(
                context, callback, VcodeResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u014.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 验证验证码
     *
     * @author Sandy
     * create at 16/6/23 下午4:25
     */
    public static void checkVerifyCode(Context context, VcodeDTO params, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.p002.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 重置密码
     *
     * @author Sandy
     * create at 16/6/21 下午5:00
     */
    public static void reset(Context context, RegisterDTO params, CallBack<LoginResult> callback) {
        AsyncCallBack<LoginResult> asyncCallBack = new AsyncCallBack<LoginResult>(
                context, callback, LoginResult.class);
        //        params.setDevicetoken("387fhjdkg0jt4");
        //        params.setPushswitch("0");
        //        params.setPushmode("2");
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u013.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 修改密码
     *
     * @author Sandy
     * create at 16/6/21 下午5:00
     */
    public static void changePwd(Context context, ChangePwdDTO params, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u012.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 退出
     *
     * @author Sandy
     * create at 16/6/8 下午11:48
     */
    public static void logout(Context context, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<>(context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u002.01"));
        LogoutDTO dto = new LogoutDTO();

        dto.setUsrid(MainRouter.getUserID(false));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void getPayHistory(Context context, PayHistoryDTO params, CallBack<PayHistoryResult> callback) {
        params.setFilenum(RSAUtils.strByEncryption(params.getFilenum(), true));
        AsyncCallBack<PayHistoryResult> asyncCallBack = new AsyncCallBack<PayHistoryResult>(
                context, callback, PayHistoryResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.v001.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void getPayCars(Context context, LogoutDTO params, CallBack<PayCarResult> callback) {
        AsyncCallBack<PayCarResult> asyncCallBack = new AsyncCallBack<PayCarResult>(
                context, callback, PayCarResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.c002.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 订单查询
     *
     * @author Sandy
     * create at 16/6/6 下午2:00
     */
    public static void getOrderList(Context context, OrderDTO params, CallBack<OrderResult> callback) {
        AsyncCallBack<OrderResult> asyncCallBack = new AsyncCallBack<OrderResult>(
                context, callback, OrderResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.i001.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 保险签单
     *
     * @author Sandy
     * create at 16/6/24 上午11:03
     */
    public static void commitInsOrder(Context context, InsOrderDTO params, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.i004.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 查询订单状态
     *
     * @author Sandy
     * create at 16/6/24 上午11:03
     */
    public static void checkOrder(Context context, CheckOrderDTO params, CallBack<CheckOrderResult> callback) {
        AsyncCallBack<CheckOrderResult> asyncCallBack = new AsyncCallBack<CheckOrderResult>(
                context, callback, CheckOrderResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.i006.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 提交邀请码
     *
     * @author Sandy
     * create at 16/6/6 下午2:00
     */
    public static void commitPersonInfo(Context context, PersonInfoDTO dto, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        dto.setDevicetoken(MainRouter.getPhoneDeviceId());
        dto.setPushswitch(0);//不推送
        dto.setUsrid(MainRouter.getUserID(true));
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u003.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 提交意见反馈
     *
     * @author Sandy
     * create at 16/6/15 下午4:05
     */
    public static void commitFeedback(Context context, FeedbackDTO dto, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u008.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 获取版本号
     *
     * @author Sandy
     * create at 16/6/15 下午4:05
     */
    public static void getCurrentVerson(Context context, VersionDTO dto, CallBack<VersionResponse> callback) {
        BaseCallBack<VersionResponse> versionCallBack = new BaseCallBack<VersionResponse>(
                context, callback, VersionResponse.class);
        post(context, getDownUrl("versionInfo"), dto, versionCallBack);
    }

    /**
     * 分享
     */
    public static void getCurrentShare(Context context, CallBack<VersionResponse> callback) {
        ShareDTO dto = new ShareDTO();
        dto.setUserPhone(RSAUtils.strByEncryption("18410109993", false));
        dto.setAcceptPhone(RSAUtils.strByEncryption("18410109997", false));
        BaseCallBack<VersionResponse> versionCallBack = new BaseCallBack<VersionResponse>(
                context, callback, VersionResponse.class);
        post(context, getDownUrl("userShare"), dto, versionCallBack);
    }

    public static void addCarInfo(Context context, CarInfoDTO dto, CallBack<BankResponse> callback) {
        dto.setCarnum(RSAUtils.strByEncryption(dto.getCarnum(), true));
        dto.setEnginenum(RSAUtils.strByEncryption(dto.getEnginenum(), true));

        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();

        t.setSYS_HEAD(getBean("cip.cfc.u005.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void bindCard(Context context, BindCardDTO dto, CallBack<BindCardResult> callback) {
        AsyncCallBack<BindCardResult> asyncCallBack = new AsyncCallBack<BindCardResult>(
                context, callback, BindCardResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u004.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void editCarInfo(Context context, CarInfoDTO dto, CallBack<BankResponse> callback) {
        dto.setCarnum(RSAUtils.strByEncryption(dto.getCarnum(), true));
        dto.setEnginenum(RSAUtils.strByEncryption(dto.getEnginenum(), true));

        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.c001.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void getCarInfo(Context context, UserCarsDTO dto, CallBack<UserCarsResult> callback) {
        AsyncCallBack<UserCarsResult> asyncCallBack = new AsyncCallBack<UserCarsResult>(
                context, callback, UserCarsResult.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.c003.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void html(Context context, String serviceCode, String reqinfo, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        MessageFormat.getInstance().setTransServiceCode(serviceCode);
        try {
            MessageFormat.getInstance().setHtmlMessageJSONObject(reqinfo);
        } catch (Exception e) {

        }

        htmlpost(context, BuildConfig.BASE_URL, MessageFormat.getInstance().getMessageFormat(), asyncCallBack);
    }

    /**
     * 更新用户头像
     *
     * @author zj
     * create at 16/10/08 下午4:05
     */
    public static void updateUserHeadImg(Context context, UpdateUserHeadImgDTO dto, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.u003.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void getViolation(Context context, ViolationDTO dto, CallBack<BankResponse> callback) {
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.v002.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void searchViolation(Context context, ViolationDTO dto, CallBack<ViolationResultParent> callback) {
        AsyncCallBack<ViolationResultParent> asyncCallBack = new AsyncCallBack<ViolationResultParent>(
                context, callback, ViolationResultParent.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.v002.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    public static void setJiaoYiDaiMa(Context context, String strFileNum, CallBack<BankResponse> callback) {
        JiaoYiDaiMaDTO jiaoYiDaiMaDTO = new JiaoYiDaiMaDTO();
        jiaoYiDaiMaDTO.setFilenum(RSAUtils.strByEncryption(strFileNum, true));
        AsyncCallBack<BankResponse> asyncCallBack = new AsyncCallBack<BankResponse>(
                context, callback, BankResponse.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean("cip.cfc.v004.01"));
        t.setReqInfo(jiaoYiDaiMaDTO);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

}
