package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface ICouponService {

    /**
     * 57.获取指定类型优惠券
     */
    @FormUrlEncoded
    @POST("february/getConponByType")
    Observable<CouponResponse> getConponByType(@Field("userId") String userId,
                                               @Field("type") String type);

}
