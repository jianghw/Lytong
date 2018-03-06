package com.zantong.mobilecttx.api;

import com.tzly.ctcyh.java.response.BankResponse;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;

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
    @POST("mobilecall_call")
    Observable<LicenseResponseBean> driverLicenseCheckGrade(@Field("msg") String requestDTO);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<ViolationNumBean> numberedQuery(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<UserCarsResult> getRemoteCarInfo(@Field("msg") String requestDTO);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<BankResponse> commitCarInfoToOldServer(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<ViolationResultParent> searchViolation(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<PayCarResult> getPayCars(@Field("msg") String msg);

    /**
     * 违章查询详情
     */
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<ViolationDetailsBean> violationDetails_v003(@Field("msg") String msg);
}
