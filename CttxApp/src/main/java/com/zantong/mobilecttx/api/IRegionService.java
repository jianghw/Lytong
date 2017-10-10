package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.order.bean.OrderExpressResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IRegionService {

    /**
     * 32.获取地区列表
     */
    @GET("region/getAllAreas")
    Observable<OrderExpressResponse> getAllAreas();

}
