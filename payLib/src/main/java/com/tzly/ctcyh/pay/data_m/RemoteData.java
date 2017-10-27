package com.tzly.ctcyh.pay.data_m;

import android.support.annotation.Nullable;

import com.tzly.ctcyh.pay.api.ICouponService;
import com.tzly.ctcyh.pay.api.IOrderService;
import com.tzly.ctcyh.pay.api.IPayService;
import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
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
    public Observable<CouponResponse> getCouponByType(String userId, String extraType) {
        return baseRetrofit().create(ICouponService.class).getConponByType(userId, extraType);
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
        return couponUserId == 0 ? baseRetrofit().create(IPayService.class).getBankPayHtml(extraOrderId, amount)
                : baseRetrofit().create(IPayService.class).getBankPayHtml(extraOrderId, amount, String.valueOf(couponUserId));
    }


}
