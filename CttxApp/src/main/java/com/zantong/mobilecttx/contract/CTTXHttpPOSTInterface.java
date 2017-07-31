package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.home.bean.VersionBean;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.SmsBean;

import okhttp3.ResponseBody;
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
    Observable<CTTXInsurancePayBean> insurancePay(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<UpdateInfo> loadUpdateInfo(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<UpdateInfo> loadVerificationCode(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("mobilecall_call")
    Observable<VersionBean> loadVersion(@Field("msg") String msg);

    @Streaming
    @GET
    Observable<ResponseBody> fileDownLoad(@Url String fileUrl);


}
