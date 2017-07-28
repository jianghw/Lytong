package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.bean.BaseResult;
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

    @POST("cttx/addVehiclelicense")
    Observable<VehicleLicenseResult> addVehicleLicense(@Body List<BindCarDTO> dtoList);
}
