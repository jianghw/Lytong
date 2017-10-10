package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.home.bean.StartPicResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 启动页面服务器接口
 */

public interface ISplashService {

    /**
     * 40.app启动图片获取
     */
    @GET("start/getPic")
    Observable<StartPicResponse> startGetPic(@Query("picNum") String picNum);
}
