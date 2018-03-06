package com.tzly.ctcyh.pay.api;

import com.tzly.ctcyh.java.response.violation.ViolationNumBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 优惠卷服务器接口
 */

public interface IBankService {

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<ViolationNumBean> bank_v003_01(@Field("msg") String msg);
}
