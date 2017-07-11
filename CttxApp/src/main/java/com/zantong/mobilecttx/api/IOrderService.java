package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;
import com.zantong.mobilecttx.order.bean.OrderListResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IOrderService {

    /**
     * 8.查询订单列表
     */
    @FormUrlEncoded
    @POST("order/getOrderList")
    Observable<OrderListResult> getOrderList(@Field("userNum") String userId);

    /**
     * 9.获取订单详情
     */
    @FormUrlEncoded
    @POST("order/getOrderDetail")
    Observable<OrderDetailResult> getOrderDetail(@Field("orderId") String orderId);

    /**
     * 10.更新订单状态
     */
    @FormUrlEncoded
    @POST("order/updateOrderStatus")
    Observable<BaseResult> updateOrderStatus(@Field("orderId") String orderId, @Field("orderStatus") String orderStatus);
}
