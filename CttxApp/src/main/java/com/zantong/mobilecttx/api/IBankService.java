package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 银行服务器接口
 */

public interface IBankService {

    @FormUrlEncoded
    @POST("mobilecall_call/mobilecall_call")
    Observable<LicenseResponseBean> driverLicenseCheckGrade(@Field("msg") String requestDTO);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<LicenseResponseBean> loadLoginPostTest(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<UserCarsResult> getRemoteCarInfo(@Field("msg") String requestDTO);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<Result> commitCarInfoToOldServer(@Field("msg") String msg);
}
