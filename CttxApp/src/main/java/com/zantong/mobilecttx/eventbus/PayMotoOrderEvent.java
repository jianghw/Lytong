package com.zantong.mobilecttx.eventbus;

/**
 * 跳转支付页面
 */
public class PayMotoOrderEvent {

    private final String orderId;
    private final String amount;
    private final String coupon;

    public PayMotoOrderEvent(String coupon, String orderId, String amount) {
        this.coupon = coupon;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAmount() {
        return amount;
    }

    public String getCoupon() {
        return coupon;
    }
}
