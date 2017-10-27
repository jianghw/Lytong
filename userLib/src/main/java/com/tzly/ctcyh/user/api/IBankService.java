package com.tzly.ctcyh.user.api;


import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.response.LoginResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 登录模块
 */
public interface IBankService {
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<LoginResponse> loadPost(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<BankResponse> loginV004(@Field("msg") String msg);

}
