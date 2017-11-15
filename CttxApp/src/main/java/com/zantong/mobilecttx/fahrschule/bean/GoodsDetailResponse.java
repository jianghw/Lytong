package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * 4.获取区域商品列表
 */
public class GoodsDetailResponse extends BaseResponse {

    private GoodsDetailBean data;

    public void setData(GoodsDetailBean data) {
        this.data = data;
    }

    public GoodsDetailBean getData() {
        return data;
    }
}
