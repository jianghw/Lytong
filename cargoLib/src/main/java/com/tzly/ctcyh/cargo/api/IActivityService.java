package com.tzly.ctcyh.cargo.api;


import com.tzly.ctcyh.cargo.bean.response.ReceiveCouponResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IActivityService {
    /**
     * 领券
     */
    @FormUrlEncoded
    @POST("activity/receiveCoupon")
    Observable<ReceiveCouponResponse> receiveCoupon(@Field("userId") String rasUserID,
                                                    @Field("couponId") String couponId,
                                                    @Field("channel") String channel);
}