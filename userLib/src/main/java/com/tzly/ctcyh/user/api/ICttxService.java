package com.tzly.ctcyh.user.api;

import com.tzly.ctcyh.user.bean.BaseResponse;
import com.tzly.ctcyh.user.bean.request.RegisterDTO;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface ICttxService {

    /**
     * 8.用户注册修改接口
     */
    @POST("cttx/register")
    Observable<BaseResponse> register(@Body RegisterDTO registerDTO);
}
