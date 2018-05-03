package com.zantong.mobilecttx.api;

import com.tzly.ctcyh.java.response.module.NewsFlagResponse;
import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
import com.zantong.mobilecttx.base.bean.ModuleBannerResponse;
import com.zantong.mobilecttx.home.bean.ModuleResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 * 服务器接口
 */

public interface IModuleService {

    /**
     * 25.模块化接口
     */
    @GET("module/moduleTree")
    Observable<ModuleResponse> moduleTree();

    /**
     * 模块配置接口
     */
    @GET("module/getBanner")
    Observable<ModuleBannerResponse> getBanner();

    /**
     * 资讯列表news/findByType
     * 参数:type(int) 1.推荐 2.新闻 3.加油 4.二手车 5.车险
     */
    @GET("news/findByType")
    Observable<NewsInfoResponse> findByType(@Query("type") int type);

    /**
     * 资讯Icons
     */
    @GET("module/getIcons")
    Observable<ModuleBannerResponse> getIcons();

    @GET("module/getNavigations")
    Observable<ModuleBannerResponse> getNavigations();

    @GET("module/newsFlag")
    Observable<NewsFlagResponse> newsFlag();
}
