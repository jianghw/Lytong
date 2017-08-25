package com.zantong.mobilecttx.api;

import com.zantong.mobilecttx.home.bean.ModuleResult;

import retrofit2.http.GET;
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
    Observable<ModuleResult> moduleTree();

}
