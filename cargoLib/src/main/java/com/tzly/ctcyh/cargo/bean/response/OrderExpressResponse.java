package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.util.List;

/**
 * 32.获取地区列表
 */
public class OrderExpressResponse extends BaseResponse {

    private List<OrderExpressBean> data;

    public void setData(List<OrderExpressBean> data) {
        this.data = data;
    }

    public List<OrderExpressBean> getData() {
        return data;
    }
}
