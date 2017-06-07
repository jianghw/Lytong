package com.zantong.mobilecttx.api;


import com.zantong.mobilecttx.user.bean.LicenseResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IAnShengService {

    @FormUrlEncoded
    @POST("mobilecall_call/mobilecall_call")
    Observable<LicenseResponseBean> driverLicenseCheckGrade(@Field("msg") String requestDTO);
}
