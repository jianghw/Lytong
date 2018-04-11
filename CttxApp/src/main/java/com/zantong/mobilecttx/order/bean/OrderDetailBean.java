package com.zantong.mobilecttx.order.bean;

/**
 * Created by jianghw on 2017/7/10.
 * Description:
 * Update by:
 * Update day:
 */

public class OrderDetailBean {

    /**
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
     * detail	string	订单详情
     * sendOffExpress	string	发出单号
     * sendBackExpress	string	发回单号
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
    private String detail;
    private String sendOffExpress;
    private String sendBackExpress;

    private String addressDetail;
    private String phone;
    private String sheng;
    private String shi;
    private String xian;

    private String discount;
    private String goodsId;
    private String merchantId;
    private String name;
    private String couponId;
    private String uid;

    private String backExpressNo;
    private String backExpressType;

    public String getBackExpressNo() {
        return backExpressNo;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getPhone() {
        return phone;
    }

    public String getSheng() {
        return sheng;
    }

    public String getShi() {
        return shi;
    }

    public String getXian() {
        return xian;
    }

    public String getDiscount() {
        return discount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getName() {
        return name;
    }

    public String getCouponId() {
        return couponId;
    }

    public String getUid() {
        return uid;
    }

    public String getSendOffExpress() {
        return sendOffExpress;
    }

    public void setSendOffExpress(String sendOffExpress) {
        this.sendOffExpress = sendOffExpress;
    }

    public String getSendBackExpress() {
        return sendBackExpress;
    }

    public void setSendBackExpress(String sendBackExpress) {
        this.sendBackExpress = sendBackExpress;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
