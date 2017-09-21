package com.zantong.mobilecttx.api;

import com.tzly.annual.base.bean.BaseResult;
import com.zantong.mobilecttx.home.bean.HomeCarResult;
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
    Observable<HomeCarResult> getTextNoticeInfo(@Query("userId") String usrid);

    /**
     * 处理违章信息
     */
    @POST("text/handleViolations")
    Observable<BaseResult> HandleViolationDTO(@Body ViolationCarDTO violationResult);
}
