package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.fahrschule.bean.ServerTimeResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IDriverTrainService {
    /**
     * 20.新手陪练获取服务地区
     */
    @GET("driverTrain/getServiceArea")
    Observable<SparringAreaResponse> getServiceArea();

    /**
     * 21.获取服务器时间
     */
    @GET("driverTrain/getServerTime")
    Observable<ServerTimeResponse> getServerTime();

}
