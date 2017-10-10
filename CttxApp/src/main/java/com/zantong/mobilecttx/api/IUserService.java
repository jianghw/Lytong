package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.home.bean.DriverCoachResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IUserService {
    /**
     * 13.判断是否为司机
     */
    @FormUrlEncoded
    @POST("user/getDriverCoach")
    Observable<DriverCoachResponse> getDriverCoach(@Field("userPhone") String userPhone);
}
