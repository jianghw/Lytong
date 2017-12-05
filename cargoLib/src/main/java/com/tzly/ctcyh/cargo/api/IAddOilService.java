package com.tzly.ctcyh.cargo.api;


import com.tzly.ctcyh.cargo.bean.BaseResponse;
import com.tzly.ctcyh.cargo.bean.request.BindCarDTO;
import com.tzly.ctcyh.cargo.bean.request.BindDrivingDTO;
import com.tzly.ctcyh.cargo.bean.request.RefuelOilDTO;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * 创建订单
     */
    @POST("addOil/createOrder")
    Observable<RefuelOrderResponse> createOrder(@Body RefuelOilDTO oilDTO);

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
}
