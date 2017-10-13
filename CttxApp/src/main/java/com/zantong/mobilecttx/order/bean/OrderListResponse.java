package com.zantong.mobilecttx.order.bean;

import cn.qqtheme.framework.bean.BaseResponse;

import java.util.List;

/**
 * 8.查询订单列表 N
 */
public class OrderListResponse extends BaseResponse {

    private List<OrderListBean> data;

    public void setData(List<OrderListBean> data) {
        this.data = data;
    }

    public List<OrderListBean> getData() {
        return data;
    }
}