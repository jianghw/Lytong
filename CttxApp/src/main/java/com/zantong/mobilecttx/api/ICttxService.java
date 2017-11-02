package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.car.bean.VehicleLicenseResponse;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface ICttxService {

    /**
     * 1.首页信息
     */
    @POST("cttx/homePage")
    Observable<HomeResponse> homePage(@Body HomeDataDTO id);

    /**
     * 48.绑定行驶证接口
     */
    @POST("cttx/bindingVehicle")
    Observable<BaseResponse> commitCarInfoToNewServer(@Body BindCarDTO bindCarDTO);

    /**
     * 19.同步银行车辆
     */
    @POST("cttx/addOrUpdateVehiclelicense")
    Observable<VehicleLicenseResponse> addOrUpdateVehicleLicense(@Body List<BindCarDTO> dtoList);

    /**
     * 16.新增车辆
     */
    @POST("cttx/addVehiclelicense")
    Observable<BaseResponse> addVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 18.删除车辆
     */
    @POST("cttx/removeVehiclelicense")
    Observable<BaseResponse> removeVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 17.编辑车辆
     */
    @POST("cttx/updateVehiclelicense")
    Observable<BaseResponse> updateVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 24.获取年检网点
     */
    @POST("cttx/annualinspectionList")
    Observable<YearCheckResponse> annualInspectionList(@Body AnnualDTO annualDTO);

    /**
     * 获取年检一条信息
     */
    @GET("cttx/annualinspection/{id}")
    Observable<YearCheckDetailResponse> annualInspection(@Path("id") int id);

    /**
     * 获得加油站地点详情
     */
    @GET("cttx/gasStation/{id}")
    Observable<GasStationDetailResponse> gasStation(@Path("id") int id);

    /**
     * 23.获取加油网点
     */
    @POST("cttx/gasStationList")
    Observable<GasStationResponse> gasStationList(@Body AnnualDTO annualDTO);

    /**
     * 是否提供活动
     */
    @GET("banner/getIndexLayer")
    Observable<HomeCarResponse> getIndexLayer();
}
