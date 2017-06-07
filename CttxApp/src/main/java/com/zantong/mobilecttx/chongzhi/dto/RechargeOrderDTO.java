package com.zantong.mobilecttx.chongzhi.dto;

/**
 * 充值订单列表请求实体
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class RechargeOrderDTO {

    private String userId; //用户ID
    private int currentIndex; //当前页
    private int orderStatus; //订单状态

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
