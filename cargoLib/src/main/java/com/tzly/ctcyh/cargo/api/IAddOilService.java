package com.tzly.ctcyh.cargo.api;


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
}
