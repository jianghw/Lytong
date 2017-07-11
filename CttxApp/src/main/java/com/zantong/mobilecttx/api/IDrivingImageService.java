package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IDrivingImageService {

    /**
     * 55.行驶证扫描接口
     */
    @Multipart
    @POST("PIM_DRIVING/SrvXMLAPI")
    Observable<DrivingOcrResult> uploadDrivingImg(@Part() MultipartBody.Part part);
}
