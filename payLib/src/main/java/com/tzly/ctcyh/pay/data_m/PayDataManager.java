package com.tzly.ctcyh.pay.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.java.request.RequestHeadDTO;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.tzly.ctcyh.pay.response.CodeDetailResponse;
import com.tzly.ctcyh.pay.response.CouponCodeResponse;
import com.tzly.ctcyh.pay.response.CouponDetailResponse;
import com.tzly.ctcyh.pay.response.CouponResponse;
import com.tzly.ctcyh.pay.response.CouponStatusResponse;
import com.tzly.ctcyh.pay.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.response.PayTypeResponse;
import com.tzly.ctcyh.pay.response.PayUrlResponse;
import com.tzly.ctcyh.pay.response.PayWeixinResponse;
import com.tzly.ctcyh.router.custom.rea.RSAUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 仓库管理类
 */

public class PayDataManager {
    @Nullable
    private static PayDataManager INSTANCE = null;
    @NonNull
    private final RemoteData mRemoteData;
    @NonNull
    private final LocalData mLocalData;

    /**
     * 懒汉式，线程不安全
     */
    public static PayDataManager getInstance(RemoteData remoteData, LocalData localData) {
        if (INSTANCE == null) {
            INSTANCE = new PayDataManager(remoteData, localData);
        }
        return INSTANCE;
    }

    private PayDataManager(@NonNull RemoteData remoteData, @NonNull LocalData localData) {
        mRemoteData = remoteData;
        mLocalData = localData;
    }

    /**
     * 57.获取指定类型优惠券
     */
    public Observable<CouponResponse> getCouponByType(String userId, String extraType, int payType) {
        return mRemoteData.getCouponByType(userId, extraType, payType);
    }

    /**
     * 31.创建订单后获取订单信息
     */
    public Observable<PayTypeResponse> getOrderInfo(String extraOrderId) {
        return mRemoteData.getOrderInfo(extraOrderId);
    }

    public String getRASUserID() {
        return RSAUtils.strByEncryption(getUserID(), true);
    }

    /**
     * 用户id
     */
    public String getUserID() {
        return mLocalData.getUserID();
    }

    /**
     * 5.获取工行支付页面
     */
    public Observable<PayUrlResponse> getBankPayHtml(String extraOrderId, String amount, int couponUserId) {
        return mRemoteData.getBankPayHtml(extraOrderId, amount, couponUserId);
    }

    /**
     * 9.获取订单详情
     */
    public Observable<OrderDetailResponse> getOrderDetail(String orderId) {
        return mRemoteData.getOrderDetail(orderId);
    }

    /**
     * 获取优惠券列表
     */
    public Observable<CouponStatusResponse> couponUserList(String rasUserID, String couponStatus) {
        return mRemoteData.couponUserList(rasUserID, couponStatus);
    }

    /**
     * 2.4.27删除用户优惠券
     */
    public Observable<BaseResponse> delUsrCoupon(String rasUserID, String couponId) {
        return mRemoteData.delUsrCoupon(rasUserID, couponId);
    }

    /**
     * 优惠券详情
     */
    public Observable<CouponDetailResponse> couponDetail(String couponId) {
        return mRemoteData.couponDetail(couponId);
    }

    /**
     * 微信支付
     */
    public Observable<PayWeixinResponse> weChatPay(String orderId, String amount, int couponUserId) {
        return mRemoteData.weChatPay(orderId, amount, couponUserId);
    }

    /**
     * 1． 码券列表
     */
    public Observable<CouponCodeResponse> getCodeList(String rasUserID, String couponStatus) {
        return mRemoteData.getCodeList(rasUserID, couponStatus);
    }

    /**
     * 删除码券
     */
    public Observable<BaseResponse> deleteCode(String codeId, String rasUserID) {
        return mRemoteData.deleteCode(codeId, rasUserID);
    }

    /**
     * .码券详情
     */
    public Observable<CodeDetailResponse> getCodeDetail(String codeId) {
        return mRemoteData.getCodeDetail(codeId);
    }

    public RequestHeadDTO requestHeadDTO(String urlCode) {
        RequestHeadDTO dto = mLocalData.requestHeadDTO();
        dto.setTransServiceCode(urlCode);
        return dto;
    }

    /**
     * 这段加密封装
     */
    public String rasByStr(String str) {
        return mLocalData.rasByStr(str);
    }

    public Observable<ViolationNumBean> bank_v003_01(String msg) {
        return mRemoteData.bank_v003_01(msg);
    }

    /**
     * 46.更新违章缴费状态
     */
    public Observable<BaseResponse> updateState(List<ViolationNum> json) {
        return mRemoteData.updateState(json);
    }

    /**
     * 分享人优惠券信息
     */
    public Observable<CouponInfoResponse> getCouponInfo(String orderId) {
        return mRemoteData.getCouponInfo(orderId);
    }
}
