package com.zantong.mobile.api;

import com.zantong.mobile.weizhang.bean.AddVehicleBean;
import com.zantong.mobile.card.bean.BindCardBean;
import com.zantong.mobile.card.bean.BindCardStepBean;
import com.zantong.mobile.user.bean.CTTXInsurancePayBean;
import com.zantong.mobile.car.bean.CanPayCarBean;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.card.bean.OpenQueryBean;
import com.zantong.mobile.user.bean.SmsBean;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.home.bean.VersionBean;
import com.zantong.mobile.weizhang.bean.ViolationDetailsBean;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface CTTXHttpPOSTInterface {
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<LoginInfoBean> loadPost(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<SmsBean> loadSmsPost(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<AddVehicleBean> loadAddVehiclePost(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<OpenQueryBean> loadOpenQueryBean(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<CTTXInsurancePayBean> insurancePay(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<AddVehicleBean> loadOpenIllegalQueryBean(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<UpdateInfo> loadUpdateInfo(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<BindCardStepBean> loadBindCard(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<UpdateInfo> loadVerificationCode(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<ViolationDetailsBean> loadViolationDetails(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<CanPayCarBean> loadCanPayCar(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<BindCardBean> loadBindCardSuccess(@Field("msg") String msg);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<BindCardBean> loadCTTXLifeLoopImage(@Body RequestBody body);
    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<VersionBean> loadVersion(@Field("msg") String msg);
    @Streaming
    @GET
    Observable<ResponseBody> fileDownLoad(@Url String fileUrl);





    @FormUrlEncoded
    @POST("http://192.9.200.131:9000/")
    Observable<CanPayCarBean> loadFile(@Field("msg") String msg);
}
