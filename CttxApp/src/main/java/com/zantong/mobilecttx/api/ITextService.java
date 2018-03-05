package com.zantong.mobilecttx.api;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface ITextService {
    /**
     * 获取违章信息
     */
    @GET("text/getTextNoticeInfo")
    Observable<HomeCarResponse> getTextNoticeInfo(@Query("userId") String usrid);

    /**
     * 处理违章信息
     */
    @POST("text/handleViolations")
    Observable<BaseResponse> HandleViolationDTO(@Body ViolationCarDTO violationResult);
}
