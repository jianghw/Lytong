package com.zantong.mobilecttx.fahrschule.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

/**
 * 4.获取区域商品列表
 */
public class GoodsDetailResult extends BaseResult {

    private GoodsDetailBean data;

    public void setData(GoodsDetailBean data) {
        this.data = data;
    }

    public GoodsDetailBean getData() {
        return data;
    }
}
