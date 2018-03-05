package com.tzly.ctcyh.pay.data_m;

import android.support.annotation.Nullable;

import com.tzly.ctcyh.pay.api.ICouponService;
import com.tzly.ctcyh.pay.api.IFebruaryService;
import com.tzly.ctcyh.pay.api.IOrderService;
import com.tzly.ctcyh.pay.api.IPayApiService;
import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CodeDetailResponse;
import com.tzly.ctcyh.pay.bean.response.CouponCodeResponse;
import com.tzly.ctcyh.pay.bean.response.CouponDetailResponse;
import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.CouponStatusResponse;
import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.bean.response.PayWeixinResponse;
import com.tzly.ctcyh.router.api.RetrofitFactory;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 远程数据处理
 */

public class RemoteData implements IRemoteSource {

    @Nullable
    private static RemoteData INSTANCE = null;

    /**
     * 懒汉式，线程不安全
     */
    public static RemoteData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteData();
        }
        return INSTANCE;
    }

    private Retrofit baseRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(1);
    }

    private Retrofit bankRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(2);
    }

    private Retrofit initBaseUrlRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(3);
    }

    private Retrofit initTestRetrofit(int type) {
        return RetrofitFactory.getInstance().createRetrofit(type);
    }

    private Retrofit initImageRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(5);
    }

    /**
     * 57.获取指定类型优惠券
     */
    @Override
    public Observable<CouponResponse> getCouponByType(String userId, String extraType, int payType) {
        return baseRetrofit().create(ICouponService.class).getCouponByType(userId, extraType, payType);
    }

    /**
     * 31.创建订单后获取订单信息
     */
    @Override
    public Observable<PayTypeResponse> getOrderInfo(String extraOrderId) {
        return baseRetrofit().create(IOrderService.class).getOrderInfo(extraOrderId);
    }

    /**
     * 5.获取工行支付页面
     */
    @Override
    public Observable<PayUrlResponse> getBankPayHtml(String extraOrderId, String amount, int couponUserId) {
        return couponUserId <= 0 ? baseRetrofit().create(IPayApiService.class).getBankPayHtml(extraOrderId, amount)
                : baseRetrofit().create(IPayApiService.class).getBankPayHtml(extraOrderId, amount, String.valueOf(couponUserId));
    }

    /**
     * 9.获取订单详情
     */
    @Override
    public Observable<OrderDetailResponse> getOrderDetail(String orderId) {
        return baseRetrofit().create(IOrderService.class).getOrderDetail(orderId);
    }

    /**
     * 获取优惠券列表
     */
    @Override
    public Observable<CouponStatusResponse> couponUserList(String rasUserID, String couponStatus) {
        return baseRetrofit().create(ICouponService.class).couponUserList(rasUserID, couponStatus);
    }

    /**
     * 2.4.27删除用户优惠券
     */
    @Override
    public Observable<BaseResponse> delUsrCoupon(String rasUserID, String couponId) {
        return baseRetrofit().create(ICouponService.class).delUsrCoupon(rasUserID, couponId);
    }

    /**
     * 优惠券详情
     */
    @Override
    public Observable<CouponDetailResponse> couponDetail(String couponId) {
        return baseRetrofit().create(ICouponService.class).couponDetail(couponId);
    }

    /**
     * 微信支付
     */
    @Override
    public Observable<PayWeixinResponse> weChatPay(String orderId, String amount, int couponUserId) {
        return couponUserId <= 0 ? baseRetrofit().create(IPayApiService.class).weChatPay(orderId, amount)
                : baseRetrofit().create(IPayApiService.class).weChatPay(orderId, amount, String.valueOf(couponUserId));
    }

    /**
     * 码券列表
     */
    @Override
    public Observable<CouponCodeResponse> getCodeList(String rasUserID, String couponStatus) {
        return baseRetrofit().create(IFebruaryService.class).getCodeList(rasUserID, couponStatus);
    }

    /**
     * 删除码券
     */
    @Override
    public Observable<BaseResponse> deleteCode(String codeId, String rasUserID) {
        return baseRetrofit().create(IFebruaryService.class).deleteCode(codeId, rasUserID);
    }

    /**
     * .码券详情
     */
    @Override
    public Observable<CodeDetailResponse> getCodeDetail(String codeId) {
        return baseRetrofit().create(IFebruaryService.class).getCodeDetail(codeId);
    }


}
