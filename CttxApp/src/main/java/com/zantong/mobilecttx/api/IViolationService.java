package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.weizhang.dto.ViolationPayDTO;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 违章处理接口
 */

public interface IViolationService {
    /**
     * 43.生成违章缴费订单
     */
    @POST("payment/createOrder")
    Observable<PayOrderResult> paymentCreateOrder(@Body ViolationPayDTO payDTO);

    @GET("payment/payForWapb2cPay")
    Observable<PayOrderResult> onPayOrderByCoupon(@Query("orderid") String orderid,
                                                  @Query("amount") String amount,
                                                  @Query("payType") String payType);
}
