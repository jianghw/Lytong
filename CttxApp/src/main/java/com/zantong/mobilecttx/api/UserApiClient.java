package com.zantong.mobilecttx.api;

import android.content.Context;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.bean.VersionResult;
import com.zantong.mobilecttx.home.dto.VersionDTO;
import com.zantong.mobilecttx.user.bean.CheckOrderResult;
import com.zantong.mobilecttx.user.bean.LoginResult;
import com.zantong.mobilecttx.user.bean.OrderResult;
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
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import cn.qqtheme.framework.util.LogUtils;


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
    public static RequestHeadDTO getBean(Context context, String str) {
        RequestHeadDTO dto = new RequestHeadDTO();
        dto.setConsumerId("04");
        dto.setTransServiceCode(str);
        dto.setRequestDate(DateUtils.getDate());
        dto.setRequestTime(DateUtils.getTime());
        dto.setConsumerSeqNo(StringUtils.getRandomStr());
        dto.setDvcToken(Tools.getIMEI(context));
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
        t.setSYS_HEAD(getBean(context, "cip.cfc.u001.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 注册
     *
     * @author Sandy
     * create at 16/6/21 下午5:00
     */
    public static void register(Context context, RegisterDTO params, CallBack<LoginResult> callback) {
        AsyncCallBack<LoginResult> asyncCallBack = new AsyncCallBack<LoginResult>(
                context, callback, LoginResult.class);
        if("".equals(PublicData.getInstance().imei)){
            params.setDevicetoken(PublicData.getInstance().imei);
        }
        params.setPushswitch("0");
        params.setPushmode("2");
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.u001.01"));
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
        t.setSYS_HEAD(getBean(context, "cip.cfc.u015.01"));
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
        t.setSYS_HEAD(getBean(context, "cip.cfc.u014.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 验证验证码
     *
     * @author Sandy
     * create at 16/6/23 下午4:25
     */
    public static void checkVerifyCode(Context context, VcodeDTO params, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.p002.01"));
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
        t.setSYS_HEAD(getBean(context, "cip.cfc.u013.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 修改密码
     *
     * @author Sandy
     * create at 16/6/21 下午5:00
     */
    public static void changePwd(Context context, ChangePwdDTO params, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.u012.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 退出
     *
     * @author Sandy
     * create at 16/6/8 下午11:48
     */
    public static void logout(Context context, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.u002.01"));
        LogoutDTO dto = new LogoutDTO();
        LogUtils.i("userID:" + PublicData.getInstance().userID);
        dto.setUsrid(PublicData.getInstance().userID);
        t.setReqInfo(dto);
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
        t.setSYS_HEAD(getBean(context, "cip.cfc.i001.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 保险签单
     *
     * @author Sandy
     * create at 16/6/24 上午11:03
     */
    public static void commitInsOrder(Context context, InsOrderDTO params, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.i004.01"));
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
        t.setSYS_HEAD(getBean(context, "cip.cfc.i006.01"));
        t.setReqInfo(params);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 提交邀请码
     *
     * @author Sandy
     * create at 16/6/6 下午2:00
     */
    public static void commitPersonInfo(Context context, PersonInfoDTO dto, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        dto.setDevicetoken(PublicData.getInstance().imei);
        dto.setPushswitch(0);//不推送
        dto.setUsrid(PublicData.getInstance().userID);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.u003.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 提交意见反馈
     *
     * @author Sandy
     * create at 16/6/15 下午4:05
     */
    public static void commitFeedback(Context context, FeedbackDTO dto, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.u008.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     * 获取版本号
     *
     * @author Sandy
     * create at 16/6/15 下午4:05
     */
    public static void getCurrentVerson(Context context, VersionDTO dto, CallBack<VersionResult> callback) {
        BaseCallBack<VersionResult> versionCallBack = new BaseCallBack<VersionResult>(
                context, callback, VersionResult.class);
        post(context, getDownUrl("versionInfo"), dto, versionCallBack);
    }

    /**
     * 分享
     *
     * @author Sandy
     * create at 16/6/15 下午4:05
     */
    public static void getCurrentShare(Context context, CallBack<VersionResult> callback) {
        ShareDTO dto = new ShareDTO();
        dto.setUserPhone(RSAUtils.strByEncryption(context, "18410109993", false));
        dto.setAcceptPhone(RSAUtils.strByEncryption(context, "18410109997", false));
        BaseCallBack<VersionResult> versionCallBack = new BaseCallBack<VersionResult>(
                context, callback, VersionResult.class);
        post(context, getDownUrl("userShare"), dto, versionCallBack);
    }


    /**
     * @author Sandy
     * create at 16/6/15 下午4:05
     */
    public static void html(Context context, String serviceCode, String reqinfo, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
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
    public static void updateUserHeadImg(Context context, UpdateUserHeadImgDTO dto, CallBack<Result> callback) {
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.u003.01"));
        t.setReqInfo(dto);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

    /**
     *
     *客户登陆APP后如果有发现有驾档，那么要保证至少成功调用一次本交易。否则后续缴费可能会出问题
     * 同步银行和交管局的违章信息
     * @author zj
     * create at 16/10/08 下午4:05
     */
    public static void setJiaoYiDaiMa(Context context, String strFileNum,CallBack<Result> callback) {
        JiaoYiDaiMaDTO jiaoYiDaiMaDTO = new JiaoYiDaiMaDTO();
        jiaoYiDaiMaDTO.setFilenum(RSAUtils.strByEncryption(context, strFileNum, true));
        AsyncCallBack<Result> asyncCallBack = new AsyncCallBack<Result>(
                context, callback, Result.class);
        RequestDTO t = new RequestDTO();
        t.setSYS_HEAD(getBean(context, "cip.cfc.v004.01"));
        t.setReqInfo(jiaoYiDaiMaDTO);
        post(context, BuildConfig.BASE_URL, t, asyncCallBack);
    }

}
