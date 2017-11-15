package com.zantong.mobilecttx.chongzhi.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.io.Serializable;

/**
 * 充值订单实体类
 * @author zyb
 *
 * ZhengYingBing LOVE ZhengLinLin FOREVER
 *
 *    *  *   *  *
 *  *      *      *
 *  *             *  猜猜我是怎么做到的
 *   *           *
 *      *     *
 *         *
 *
 *    是不是很厉害
 *
 * create at 16/12/30 下午4:15
 */
public class RechargeOrderBean extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -7620435178023928252L;

    String orderId;
    String oilType;
    String oilCardNum;
    String payType;
    String rechargeDate;
    String rechargeMoney;
    String orderType;
    int orderStatus;
    String discount;
    String points;
    String prodMoney;
    String quantity;
    String payDate;
    String rechargeTemplate;
    String userid;
    String oilCardRechargeStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public String getOilCardNum() {
        return oilCardNum;
    }

    public void setOilCardNum(String oilCardNum) {
        this.oilCardNum = oilCardNum;
    }


    public String getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getRechargeTemplate() {
        return rechargeTemplate;
    }

    public void setRechargeTemplate(String rechargeTemplate) {
        this.rechargeTemplate = rechargeTemplate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOilCardRechargeStatus() {
        return oilCardRechargeStatus;
    }

    public void setOilCardRechargeStatus(String oilCardRechargeStatus) {
        this.oilCardRechargeStatus = oilCardRechargeStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getProdMoney() {
        return prodMoney;
    }

    public void setProdMoney(String prodMoney) {
        this.prodMoney = prodMoney;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }
}
