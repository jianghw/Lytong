package com.zantong.mobilecttx.order.bean;

/**
 * Created by jianghw on 17-7-9.
 * 8.查询订单列表
 */

public class OrderListBean {

    /**
     * orderId : 17070715562186
     * type : 3 订单类型：1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练
     * amount : 10000
     * payType : 0
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3已锁定
     * remark :
     * createDate : 2017-07-07 15:56:18
     */

    private String orderId;
    private int type;
    private int amount;
    private int payType;
    private int orderStatus;
    private String remark;
    private String createDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
