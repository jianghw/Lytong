package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.base.bean.UnimpededBannerResponse;
import com.zantong.mobilecttx.home.bean.ModuleResponse;

import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<UnimpededBannerResponse> getBanner();
}
