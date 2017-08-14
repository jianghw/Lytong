package com.zantong.mobilecttx.order.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * 8.查询订单列表 N
 */
public class OrderDetailResult extends BaseResult {

    private OrderDetailBean data;

    public void setData(OrderDetailBean data) {
        this.data = data;
    }

    public OrderDetailBean getData() {
        return data;
    }
}
