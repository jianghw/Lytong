package com.zantong.mobilecttx.eventbus;

/**
 * 跳转支付页面
 */
public class PayMotoOrderEvent {

    private final String orderId;
    private final String amount;

    public PayMotoOrderEvent(String orderId, String amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAmount() {
        return amount;
    }
}
