package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.order.bean.CouponFragmentResponse;
import com.zantong.mobilecttx.order.bean.MessageResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface IFebruaryService {

    /**
     * 2.4.2查看优惠券信息
     */
    @FormUrlEncoded
    @POST("february/usrCouponInfo")
    Observable<CouponFragmentResponse> usrCouponInfo(@Field("usrnum") String usrnum,
                                                     @Field("couponStatus") String couponStatus);

    /**
     * 2.4.27删除用户优惠券
     */
    @FormUrlEncoded
    @POST("february/delUsrCoupon")
    Observable<MessageResponse> delUsrCoupon(@Field("couponId") String couponId,
                                             @Field("userId") String userId);

    /**
     * 57.获取指定类型优惠券
     */
    @FormUrlEncoded
    @POST("february/getConponByType")
    Observable<RechargeCouponResponse> getConponByType(@Field("userId") String userId, @Field("type") String type);

    /**
     * 7.获取用户指定活动的统计总数
     */
    @FormUrlEncoded
    @POST("activity/getRecordCount")
    Observable<RecordCountResponse> getRecordCount(@Field("type") String type, @Field("phone") String phone);
}
