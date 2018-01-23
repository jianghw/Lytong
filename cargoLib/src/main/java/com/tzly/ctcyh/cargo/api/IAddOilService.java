package com.tzly.ctcyh.cargo.api;


import com.tzly.ctcyh.cargo.bean.BaseResponse;
import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.NorOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface IAddOilService {

    /**
     * 加油
     */
    @GET("addOil/getGoods")
    Observable<RefuelOilResponse> getGoods();

    /**
     * 创建加油订单
     */
    @FormUrlEncoded
    @POST("order/createOrder")
    Observable<RefuelOrderResponse> createOrder(
            @Field("userNum") String userNum,
            @Field("goodsId") String goodsId,
            @Field("price") String price,
            @Field("oilCard") String oilCard,
            @Field("type") String type
    );

    /**
     * 办卡
     */
    @FormUrlEncoded
    @POST("order/createOrder")
    Observable<RefuelOrderResponse> createOrder(
            @Field("userNum") String userNum,
            @Field("goodsId") String goodsId,
            @Field("price") String price,
            @Field("type") String type,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("sheng") String sheng,
            @Field("shi") String shi,
            @Field("xian") String xian,
            @Field("addressDetail") String addressDetail
    );

    /**
     * 驾驶证
     */
    @POST("cttx/bindingDriving")
    Observable<BaseResponse> bindingDriving(@Body BindDrivingDTO drivingDTO);

    /**
     * 16.新增车辆
     */
    @POST("cttx/addVehiclelicense")
    Observable<BaseResponse> addVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 加油及充值
     */
    @FormUrlEncoded
    @POST("oil/findAndSaveCards")
    Observable<RefuelOilResponse> findAndSaveCards(@Field("userId") String rasUserID,
                                                   @Field("oilCard") String oilCard);

    /**
     * 办理油卡
     */
    @GET("handleOilCard/buyCard")
    Observable<BidOilResponse> handleOilCard();

    /**
     * 993
     */
    @GET("oil/findOilCards")
    Observable<NorOilResponse> findOilCards(@Query("userId") String rasUserID);

    @GET("oil/findCaiNiaoCard")
    Observable<NorOilResponse> findCaiNiaoCard(@Query("userId") String rasUserID);
}
