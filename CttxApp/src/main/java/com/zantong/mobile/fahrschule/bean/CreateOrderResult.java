package com.zantong.mobile.fahrschule.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 4.获取区域商品列表
 */
public class CreateOrderResult extends BaseResult {

    private CreateOrderBean data;

    public void setData(CreateOrderBean data) {
        this.data = data;
    }

    public CreateOrderBean getData() {
        return data;
    }
}
