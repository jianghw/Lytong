package com.zantong.mobilecttx.order.bean;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;

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
