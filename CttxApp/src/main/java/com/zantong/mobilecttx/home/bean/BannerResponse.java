package com.zantong.mobilecttx.home.bean;

import cn.qqtheme.framework.bean.BaseResponse;

import java.util.List;

/**
 * 优惠页面 广告
 */
public class BannerResponse extends BaseResponse {

    private List<BannerBean> data;

    public void setData(List<BannerBean> data) {
        this.data = data;
    }

    public List<BannerBean> getData() {
        return data;
    }
}
