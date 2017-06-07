package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.chongzhi.bean.RechargeResult;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;

import retrofit2.http.Body;
import retrofit2.http.POST;
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

}
