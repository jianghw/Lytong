package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 加油服务器接口
 */

public interface IAddOilService {
    /**
     * 10.创建加油订单
     */
    @POST("addOil/createOrder")
    Observable<RechargeResult> addOilCreateOrder(@Body RechargeDTO baseDTO);

    @GET("payment/payForWapb2cPay")
    Observable<PayOrderResult> onPayOrderByCoupon(@Query("orderid") String orderid,
                                                  @Query("amount") String amount,
                                                  @Query("payType") String payType);
}
