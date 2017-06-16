package com.zantong.mobilecttx.model.repository;

import com.zantong.mobilecttx.api.IAddOilService;
import com.zantong.mobilecttx.api.IAnShengService;
import com.zantong.mobilecttx.api.IFebruaryService;
import com.zantong.mobilecttx.api.IMessageService;
import com.zantong.mobilecttx.api.ISplashService;
import com.zantong.mobilecttx.api.IViolationService;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.home.bean.StartPicResult;
import com.zantong.mobilecttx.interf.CTTXHttpPOSTInterface;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.user.bean.MessageResult;
import com.zantong.mobilecttx.user.bean.MessageTypeResult;
import com.zantong.mobilecttx.user.dto.MegDTO;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 远程数据处理
 */

public class RemoteData implements IRemoteSource {
    /**
     * 单例
     */
    public static RemoteData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        static final RemoteData INSTANCE = new RemoteData();
    }

    private Retrofit initRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(1);
    }

    private Retrofit initAppUrlRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(2);
    }

    private Retrofit initBaseUrlRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(3);
    }

    private Retrofit initTestRetrofit(int type) {
        return RetrofitFactory.getInstance().createRetrofit(type);
    }

    /**
     * 消息模块
     */
    @Override
    public Observable<MessageTypeResult> messageFindAll(BaseDTO bean) {
        return initRetrofit().create(IMessageService.class).messageFindAll(bean);
    }

    @Override
    public Observable<MessageResult> findMessageDetailByMessageId(MegDTO bean) {
        return initRetrofit().create(IMessageService.class).findMessageDetailByMessageId(bean);
    }

    @Override
    public Observable<MessageDetailResult> findMessageDetail(MessageDetailDTO bean) {
        return initRetrofit().create(IMessageService.class).findMessageDetail(bean);
    }

    /**
     * 2.4.24删除消息
     */
    @Override
    public Observable<MessageResult> deleteMessageDetail(MegDTO megDTO) {
        return initRetrofit().create(IMessageService.class).deleteMessageDetail(megDTO);
    }

    /**
     * 优惠劵模块
     */
    @Override
    public Observable<CouponFragmentResult> usrCouponInfo(String usrnum, String couponStatus) {
        return initRetrofit().create(IFebruaryService.class).usrCouponInfo(usrnum, couponStatus);
    }

    /**
     * 2.4.27删除用户优惠券
     */
    @Override
    public Observable<MessageResult> delUsrCoupon(String couponId, String userId) {
        return initRetrofit().create(IFebruaryService.class).delUsrCoupon(couponId, userId);
    }

    /**
     * 查分
     *
     * @param requestDTO
     * @return
     */
    @Override
    public Observable<LicenseResponseBean> driverLicenseCheckGrade(String requestDTO) {
        return initAppUrlRetrofit().create(IAnShengService.class).driverLicenseCheckGrade(requestDTO);
    }

    /**
     * 安盛登录
     * https://ctkapptest.icbc-axa.com/ecip/mobilecall_call
     *
     * @param msg
     * @return
     */
    @Override
    public Observable<LoginInfoBean> loadLoginPost(String msg) {
        return initAppUrlRetrofit().create(CTTXHttpPOSTInterface.class).loadPost(msg);
    }

    /**
     * 40.app启动图片获取
     *
     * @param msg 图片类型(0主页图片，1其余图片)
     * @return
     */
    @Override
    public Observable<StartPicResult> startGetPic(String msg) {
        return initRetrofit().create(ISplashService.class).startGetPic(msg);
    }

    /**
     * 57.获取指定类型优惠券
     */
    @Override
    public Observable<RechargeCouponResult> getCouponByType(String userId, String type) {
        return initRetrofit().create(IFebruaryService.class).getConponByType(userId, type);
    }

    /**
     * 10.创建加油订单
     */
    @Override
    public Observable<RechargeResult> addOilCreateOrder(RechargeDTO rechargeDTO) {
        return initRetrofit().create(IAddOilService.class).addOilCreateOrder(rechargeDTO);
    }

    /**
     * 54.充值接口
     */
    @Override
    public Observable<PayOrderResult> onPayOrderByCoupon(String payUrl, String orderPrice, String payType) {
        return initRetrofit().create(IAddOilService.class).onPayOrderByCoupon(payUrl, orderPrice, payType);
    }

    /**
     * 43.生成违章缴费订单
     */
    @Override
    public Observable<PayOrderResult> paymentCreateOrder(ViolationPayDTO payDTO) {
        return initRetrofit().create(IViolationService.class).paymentCreateOrder(payDTO);
    }

    @Override
    public Observable<LicenseResponseBean> loadLoginPostTest(String msg) {
        return initAppUrlRetrofit().create(IAnShengService.class).loadLoginPostTest(msg);
    }
}
