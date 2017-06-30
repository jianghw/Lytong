package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface ICttxService {

    /**
     * 1.首页信息
     */
    @POST("cttx/homePage")
    Observable<HomeResult> homePage(@Body HomeDataDTO id);
}
