package com.tzly.ctcyh.pay.bean.response;


import com.tzly.ctcyh.pay.bean.BaseResponse;

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
