package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.bean.PayWeixinResponse;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;
import com.zantong.mobilecttx.weizhang.bean.ViolationNum;

import java.util.List;

import com.tzly.ctcyh.router.bean.BaseResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IPayService {

    /**
     * 5.获取工行支付页面
     */
    @FormUrlEncoded
    @POST("pay/getBankPayHtml")
    Observable<PayOrderResponse> getBankPayHtml(@Field("orderId") String orderId, @Field("amount") String orderPrice);

    @FormUrlEncoded
    @POST("pay/getBankPayHtml")
    Observable<PayOrderResponse> getBankPayHtml(@Field("orderId") String orderId,
                                                @Field("amount") String amount, @Field("couponUserId") String couponUserId);

    /**
     * 46.更新违章缴费状态
     *
     * @param json
     */
    @POST("payment/updateState")
    Observable<BaseResponse> updateState(@Body List<ViolationNum> json);

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
}
