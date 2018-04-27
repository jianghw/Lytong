package com.zantong.mobilecttx.base.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 首页返回实体
 */
public class ModuleBannerResponse extends BaseResponse {
    private List<ModuleBannerBean> data;

    public List<ModuleBannerBean> getData() {
        return data;
    }

    public void setData(List<ModuleBannerBean> data) {
        this.data = data;
    }
}
