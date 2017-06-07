package com.zantong.mobilecttx.user.dto;

/**
 * 充值订单取消请求实体
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class CancelRechargeOrderDTO {

    private String userId; //用户ID
    private String orderId; //订单号

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
