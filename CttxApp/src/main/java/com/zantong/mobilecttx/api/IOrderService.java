package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.order.bean.OrderDetailResponse;
import com.zantong.mobilecttx.order.bean.OrderListResponse;
import com.zantong.mobilecttx.order.bean.ReceiveInfoResponse;
import com.zantong.mobilecttx.order.dto.ExpressDTO;

import com.tzly.ctcyh.router.bean.BaseResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
    Observable<OrderListResponse> getOrderList(@Field("userNum") String userId);

    /**
     * 9.获取订单详情
     */
    @FormUrlEncoded
    @POST("order/getOrderDetail")
    Observable<OrderDetailResponse> getOrderDetail(@Field("orderId") String orderId);

    /**
     * 10.更新订单状态
     */
    @FormUrlEncoded
    @POST("order/updateOrderStatus")
    Observable<BaseResponse> updateOrderStatus(@Field("orderId") String orderId, @Field("orderStatus") String orderStatus);

    /**
     * 10.取消订单
     */
    @FormUrlEncoded
    @POST("order/cancelOrder")
    Observable<BaseResponse> cancelOrder(@Field("orderId") String orderId, @Field("userNum") String userNum);

    /**
     * 29.填写快递信息
     */
    @POST("order/addExpressInfo")
    Observable<BaseResponse> addExpressInfo(@Body ExpressDTO expressDTO);

    /**
     * 33.获取收件人信息
     */
    @GET("order/getReceiveInfo")
    Observable<ReceiveInfoResponse> getReceiveInfo(@Query("orderId") String orderId);
}
