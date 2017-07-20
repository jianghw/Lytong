package com.zantong.mobilecttx.order.bean;

/**
 * Created by jianghw on 2017/7/10.
 * Description:
 * Update by:
 * Update day:
 */

public class OrderDetailBean {


    /**
     * orderId : 17070715562186
     * type : 3
     * amount : 10000
     * payType : 0
     * orderStatus : 2
     * remark :
     * createDate : 2017-07-07 15:56:18
     * goodsName : 100元油卡
     * icon : https://www.showdoc.cc/home.png
     * merchantName : 中石化
     * detail : 订单已生成，客服将在24小时内电话和您联系确认订单信息请保持手机通畅。
     * 若有其它问题可拨打安亭驾校客服电话: 13062595466
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
