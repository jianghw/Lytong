package com.zantong.mobile.order.bean;

/**
 * Created by jianghw on 17-7-9.
 * 8.查询订单列表
 */

public class OrderListBean {

    /**
     * 参数名	类型	说明
     * orderId	string	订单ID
     * type	int	订单类型：1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练
     * amount	int	付款金额
     * payType	int	付款方式
     * orderStatus	int	订单状态,0未至付，1已支付,2取消或过期
     * remark	string	订单备注
     * createDate	string	创建时间
     * goodsName	string	商品名称
     * icon	string	供应商图标
     * merchantName	string	供应商名称
     * targetType	string	跳转类型:0-native,1-web
     * targetUrl	string	web地址
     * phone
     */

    private String orderId;
    private int type;
    private float amount;
    private int payType;
    private int orderStatus;
    private String remark;
    private String createDate;
    private String goodsName;
    private String icon;
    private String merchantName;
    private String targetType;
    private String targetUrl;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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
