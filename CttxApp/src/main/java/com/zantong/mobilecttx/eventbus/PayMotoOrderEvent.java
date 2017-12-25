package com.zantong.mobilecttx.eventbus;

/**
 * 跳转支付页面
 */
public class PayMotoOrderEvent {

    private final String orderId;
    private final String amount;
    private final String coupon;
    private final int type;

    public PayMotoOrderEvent(String coupon, String orderId, String amount, int type) {
        this.coupon = coupon;
        this.orderId = orderId;
        this.amount = amount;
        this.type = type;
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

    public int getType() {
        return type;
    }
}
