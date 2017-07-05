package com.zantong.mobilecttx.home.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.List;

/**
 * 优惠页面 广告
 */

public class BannerResult extends BaseResult {

    private List<BannerBean> data;

    public void setData(List<BannerBean> data) {
        this.data = data;
    }

    public List<BannerBean> getData() {
        return data;
    }
}
