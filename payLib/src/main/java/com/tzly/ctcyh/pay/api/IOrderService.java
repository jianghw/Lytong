package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.pay.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.response.PayTypeResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface IOrderService {

    /**
     * 57.获取指定类型优惠券
     */
    @FormUrlEncoded
    @POST("order/getOrderInfo")
    Observable<PayTypeResponse> getOrderInfo(@Field("orderId") String orderId);

    /**
     * 9.获取订单详情
     */
    @FormUrlEncoded
    @POST("order/getOrderDetail")
    Observable<OrderDetailResponse> getOrderDetail(@Field("orderId") String orderId);
}
