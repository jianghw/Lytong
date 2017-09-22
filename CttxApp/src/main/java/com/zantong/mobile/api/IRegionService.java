package com.zantong.mobile.api;

import com.zantong.mobile.order.bean.OrderExpressResult;

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
    Observable<OrderExpressResult> getAllAreas();

}
