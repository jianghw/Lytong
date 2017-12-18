package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CodeDetailResponse;
import com.tzly.ctcyh.pay.bean.response.CouponCodeResponse;
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

public interface IFebruaryService {

    /**
     * 1． 码券列表
     * /february/getCodeList POST，键值对
     * 参数：userId，status（1，可用，2失效）
     */
    @FormUrlEncoded
    @POST("february/getCodeList")
    Observable<CouponCodeResponse> getCodeList(@Field("userId") String rasUserID,
                                               @Field("status") String couponStatus);

    /**
     * 删除码券
     * /february/deleteCode   POST，键值对
     * 参数：codeId，userId
     */
    @FormUrlEncoded
    @POST("february/deleteCode")
    Observable<BaseResponse> deleteCode(@Field("codeId") String codeId, @Field("userId") String rasUserID);

    /**
     * .码券详情
     */
    @FormUrlEncoded
    @POST("february/getCodeDetail")
    Observable<CodeDetailResponse> getCodeDetail(@Field("codeId") String codeId);
}
