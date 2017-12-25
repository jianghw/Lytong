package com.zantong.mobilecttx.api;

import com.tzly.ctcyh.router.bean.BaseResponse;
import com.zantong.mobilecttx.home.bean.BannerResponse;

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
     * 58.获取banner图片
     */
    @FormUrlEncoded
    @POST("banner/getBanner")
    Observable<BannerResponse> getBanner(@Field("type") String type);

    /**
     * 统计
     */
    @FormUrlEncoded
    @POST("banner/saveStatisticsCount")
    Observable<BaseResponse> saveStatisticsCount(@Field("contentId")String contentId, @Field("userId")String rasUserID);
}
