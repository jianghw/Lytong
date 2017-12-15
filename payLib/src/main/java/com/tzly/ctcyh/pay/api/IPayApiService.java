package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;
import com.tzly.ctcyh.pay.bean.response.PayWeixinResponse;

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
                                            @Field("amount") String amount,
                                            @Field("ip") String phontIP);

    @FormUrlEncoded
    @POST("pay/weChatPay")
    Observable<PayWeixinResponse> weChatPay(@Field("orderId") String orderId,
                                         @Field("amount") String amount,
                                         @Field("ip") String phontIP,
                                         @Field("couponUserId") String couponUserId);
}
