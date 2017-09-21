package com.tzly.annual.base.bean.response;

/**
 * Created by jianghw on 2017/9/20.
 * Description:
 * Update by:
 * Update day:
 */

public class CattleOrderBean {

    /**
     * id : 1
     * orderNo : 0000000123342334
     * tradeNo : 1505187133031
     * plateformId : 1
     * orderNum : 123342334
     * orderType : 1
     * payableAmount : 100
     * payment : 90
     * state : 0
     * payTime : null
     * createTime : 2017-09-12 11:32:13
     * details : null
     */

    private int id;
    private String orderNo;
    private String tradeNo;
    private int plateformId;
    private String orderNum;
    private int orderType;
    private int payableAmount;
    private int payment;
    private int state;
    private String payTime;
    private String createTime;
    private String details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getPlateformId() {
        return plateformId;
    }

    public void setPlateformId(int plateformId) {
        this.plateformId = plateformId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(int payableAmount) {
        this.payableAmount = payableAmount;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
