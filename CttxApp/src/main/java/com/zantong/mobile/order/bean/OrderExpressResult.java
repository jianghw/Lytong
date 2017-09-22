package com.zantong.mobile.order.bean;

import java.util.List;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 32.获取地区列表
 */
public class OrderExpressResult extends BaseResult {

    private List<OrderExpressBean> data;

    public void setData(List<OrderExpressBean> data) {
        this.data = data;
    }

    public List<OrderExpressBean> getData() {
        return data;
    }
}
