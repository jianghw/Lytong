package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResult;
import com.zantong.mobilecttx.order.bean.CouponFragmentResult;
import com.zantong.mobilecttx.order.bean.MessageResult;

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
    Observable<CouponFragmentResult> usrCouponInfo(@Field("usrnum") String usrnum,
                                                   @Field("couponStatus") String couponStatus);

    /**
     * 2.4.27删除用户优惠券
     */
    @FormUrlEncoded
    @POST("february/delUsrCoupon")
    Observable<MessageResult> delUsrCoupon(@Field("couponId") String couponId,
                                           @Field("userId") String userId);

    /**
     * 57.获取指定类型优惠券
     */
    @FormUrlEncoded
    @POST("february/getConponByType")
    Observable<RechargeCouponResult> getConponByType(@Field("userId") String userId, @Field("type") String type);

    /**
     * 7.获取用户指定活动的统计总数
     */
    @FormUrlEncoded
    @POST("activity/getRecordCount")
    Observable<RecordCountResult> getRecordCount(@Field("type") String type, @Field("phone") String phone);
}
