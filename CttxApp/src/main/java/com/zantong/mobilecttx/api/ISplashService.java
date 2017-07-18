package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.home.bean.StartPicResult;

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
    Observable<StartPicResult> startGetPic(@Query("picNum") String picNum);
}
