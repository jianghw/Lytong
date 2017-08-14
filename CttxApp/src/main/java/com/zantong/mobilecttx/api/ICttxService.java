package com.zantong.mobilecttx.api;

import cn.qqtheme.framework.contract.bean.BaseResult;
import com.zantong.mobilecttx.car.bean.VehicleLicenseResult;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
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
    Observable<HomeResult> homePage(@Body HomeDataDTO id);

    /**
     * 48.绑定行驶证接口
     */
    @POST("cttx/bindingVehicle")
    Observable<BaseResult> commitCarInfoToNewServer(@Body BindCarDTO bindCarDTO);

    /**
     * 19.同步银行车辆
     */
    @POST("cttx/addOrUpdateVehiclelicense")
    Observable<VehicleLicenseResult> addOrUpdateVehicleLicense(@Body List<BindCarDTO> dtoList);

    /**
     * 16.新增车辆
     */
    @POST("cttx/addVehiclelicense")
    Observable<BaseResult> addVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 18.删除车辆
     */
    @POST("cttx/removeVehiclelicense")
    Observable<BaseResult> removeVehicleLicense(@Body BindCarDTO bindCarDTO);

    /**
     * 17.编辑车辆
     */
    @POST("cttx/updateVehiclelicense")
    Observable<BaseResult> updateVehicleLicense(@Body BindCarDTO bindCarDTO);
}
