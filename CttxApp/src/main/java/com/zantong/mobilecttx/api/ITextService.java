package com.zantong.mobilecttx.api;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.active.ActiveConfigResponse;
import com.tzly.ctcyh.java.response.violation.AdvModuleResponse;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;

import retrofit2.http.Body;
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

public interface ITextService {
    /**
     * 获取违章信息
     */
    @GET("text/getTextNoticeInfo")
    Observable<HomeCarResponse> getTextNoticeInfo(@Query("userId") String usrid);

    /**
     * 处理违章信息
     */
    @POST("text/handleViolations")
    Observable<BaseResponse> HandleViolationDTO(@Body ViolationCarDTO violationResult);

    @GET("text/findIsValidAdvert")
    Observable<ValidAdvResponse> findIsValidAdvert(@Query("carNo") String carNum);

    /**
     * 获取配置接口
     */
    @FormUrlEncoded
    @POST("config/getConfig ")
    Observable<ActiveConfigResponse> getConfig(@Field("channel") String channel);

    @FormUrlEncoded
    @POST("config/getConfig ")
    Observable<ActiveConfigResponse> getConfig(@Field("channel") String channel,
                                               @Field("registerDate") String registerDate);

    /**
     * 领券
     */
    @FormUrlEncoded
    @POST("activity/receiveCoupon")
    Observable<BaseResponse> receiveCoupon(@Field("userId") String rasUserID,
                                           @Field("couponId") String couponId,
                                           @Field("channel") String channel);

    /**
     * 广告统计
     */
    @GET("text/advertCount")
    Observable<BaseResponse> advertCount(@Query("id") String keyId, @Query("channel") String channel);

    @GET("module/moduleList")
    Observable<AdvModuleResponse> moduleList();
}
