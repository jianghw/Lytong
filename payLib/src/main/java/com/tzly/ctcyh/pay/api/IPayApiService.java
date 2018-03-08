package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;
import com.tzly.ctcyh.pay.response.PayUrlResponse;
import com.tzly.ctcyh.pay.response.PayWeixinResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface IPayApiService {

    /**
     * 5.获取工行支付页面
     */
    @FormUrlEncoded
    @POST("pay/getBankPayHtml")
    Observable<PayUrlResponse> getBankPayHtml(@Field("orderId") String orderId,
                                              @Field("amount") String amount);

    @FormUrlEncoded
    @POST("pay/getBankPayHtml")
    Observable<PayUrlResponse> getBankPayHtml(@Field("orderId") String orderId,
                                              @Field("amount") String amount,
                                              @Field("couponUserId") String couponUserId);

    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST("pay/weChatPay")
    Observable<PayWeixinResponse> weChatPay(@Field("orderId") String orderId,
                                            @Field("amount") String amount);

    @FormUrlEncoded
    @POST("pay/weChatPay")
    Observable<PayWeixinResponse> weChatPay(@Field("orderId") String orderId,
                                         @Field("amount") String amount,
                                         @Field("couponUserId") String couponUserId);

    @POST("payment/updateState")
    Observable<BaseResponse> updateState(@Body List<ViolationNum> json);
}
