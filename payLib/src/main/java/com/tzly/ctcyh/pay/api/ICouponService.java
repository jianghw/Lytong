package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CouponDetailResponse;
import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.CouponStatusResponse;

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
                                               @Field("type") String type,
                                               @Field("payType") int payType);

    /**
     * 获取优惠券列表
     */
    @FormUrlEncoded
    @POST("february/couponUserList")
    Observable<CouponStatusResponse> couponUserList(@Field("userNum") String userNum,
                                                    @Field("couponStatus") String couponStatus);

    /**
     * 2.4.27删除用户优惠券
     */
    @FormUrlEncoded
    @POST("february/delUsrCoupon")
    Observable<BaseResponse> delUsrCoupon(@Field("userId") String userId,
                                          @Field("couponId") String couponId);

    /**
     * 优惠券详情
     */
    @FormUrlEncoded
    @POST("february/couponDetail")
    Observable<CouponDetailResponse> couponDetail(@Field("couponUserId") String couponId);
}
