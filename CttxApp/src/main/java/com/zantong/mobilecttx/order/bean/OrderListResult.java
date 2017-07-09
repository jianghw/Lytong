package com.zantong.mobilecttx.order.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.List;

/**
 * 8.查询订单列表 N
 */
public class OrderListResult extends BaseResult {

    private List<OrderListBean> data;

    public void setData(List<OrderListBean> data) {
        this.data = data;
    }

    public List<OrderListBean> getData() {
        return data;
    }
}
