package com.zantong.mobile.api;

import com.zantong.mobile.weizhang.bean.PayOrderResult;

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
    Observable<PayOrderResult> getBankPayHtml(@Field("orderId") String orderId, @Field("amount") String orderPrice);
}
