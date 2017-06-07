package com.zantong.mobilecttx.daijia.bean;

/**
 * Created by zhengyingbing on 17/3/3.
 * 代驾订单item
 */

public class DaiJiaOrderListBean {
    String orderId;
    String createTime;
    String orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
