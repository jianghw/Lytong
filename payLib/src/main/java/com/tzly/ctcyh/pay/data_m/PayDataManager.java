package com.tzly.ctcyh.pay.data_m;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.router.util.rea.RSAUtils;

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
    public Observable<CouponResponse> getCouponByType(String userId, String extraType) {
        return mRemoteData.getCouponByType(userId, extraType);
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
}
