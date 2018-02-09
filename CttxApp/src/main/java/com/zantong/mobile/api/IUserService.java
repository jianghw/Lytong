package com.zantong.mobile.api;

import com.tzly.annual.base.bean.BaseResponse;
import com.tzly.annual.base.bean.response.AnnouncementResult;
import com.tzly.annual.base.bean.response.LoginResult;
import com.tzly.annual.base.bean.response.StatistCountResult;
import com.zantong.mobile.home.bean.DriverCoachResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
    Observable<DriverCoachResult> getDriverCoach(@Field("userPhone") String userPhone);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("innerUser/login")
    Observable<LoginResult> innerUserLogin(@Field("phone") String userPhone, @Field("pwd") String userPassword);

    @GET("innerUser/findAnnouncements")
    Observable<AnnouncementResult> findAnnouncements();

    @GET("innerUser/getStatisticsCount")
    Observable<StatistCountResult> getStatisticsCount(@Query("phone")String phone);
}
