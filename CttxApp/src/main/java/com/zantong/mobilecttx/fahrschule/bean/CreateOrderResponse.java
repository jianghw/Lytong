package com.zantong.mobilecttx.fahrschule.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * 4.获取区域商品列表
 */
public class CreateOrderResponse extends BaseResponse {

    private CreateOrderBean data;

    public void setData(CreateOrderBean data) {
        this.data = data;
    }

    public CreateOrderBean getData() {
        return data;
    }
}