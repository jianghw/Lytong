package com.zantong.mobilecttx.order.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * 8.查询订单列表 N
 */
public class OrderDetailResponse extends BaseResponse {

    private OrderDetailBean data;

    public void setData(OrderDetailBean data) {
        this.data = data;
    }

    public OrderDetailBean getData() {
        return data;
    }
}