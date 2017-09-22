package com.zantong.mobile.chongzhi.bean;

/**
 * 充值订单详情实体
 * @author zyb
 *
 *  
 *    *  *   *  *     
 *  *      *      *   
 *  *             *   
 *   *           *    
 *      *     *       
 *         *          
 * 
 *
 * create at 17/1/17 下午5:37
 */
public class RechargeOrderDetailBean {

    private String methodType;
    private String orderId;
    private String oilType;
    private String oilCardNum;
    private String rechargeMoney;
    private String payType;
    private String rechargeDate;
    private int orderStatus;
    private String discount;
    private String orderType;
    private String points;
    private String payDate;
    private String quantity;
    private String prodMoney;
    private String userId;
    private String rechargeTemplate;
    private String currentIndex;
    private String pageSize;
    private String oilCardRecahrgeStatus;

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

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

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProdMoney() {
        return prodMoney;
    }

    public void setProdMoney(String prodMoney) {
        this.prodMoney = prodMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRechargeTemplate() {
        return rechargeTemplate;
    }

    public void setRechargeTemplate(String rechargeTemplate) {
        this.rechargeTemplate = rechargeTemplate;
    }

    public String getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(String currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOilCardRecahrgeStatus() {
        return oilCardRecahrgeStatus;
    }

    public void setOilCardRecahrgeStatus(String oilCardRecahrgeStatus) {
        this.oilCardRecahrgeStatus = oilCardRecahrgeStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }
}
