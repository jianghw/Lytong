package com.zantong.mobile.api;

import com.zantong.mobile.home.bean.BannerResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IBannerService {
    /**
     *58.获取banner图片
     */
    @FormUrlEncoded
    @POST("banner/getBanner")
    Observable<BannerResult> getBanner(@Field("type") String type);

}
