package com.zantong.mobilecttx.fahrschule.bean;

/**
 * Created by jianghw on 2017/7/6.
 * Description:
 * Update by:
 * Update day:
 */

public class SubjectGoodsBean {


    /**
     * goodsId : 12
     * merchantId : 5
     * name : 自动挡 (工作日)
     * type : 4
     * price : 1.0
     * description : 课程介绍课程介绍\n课程介绍课程介绍
     * status : 1
     * createTime : 1499756090
     * expiredTime : 86400
     * discount : 1.0
     * keyName : class_time
     * dataValue : 4课时/8小时
     */

    private int goodsId;
    private int merchantId;
    private String name;
    private int type;
    private double price;
    private String description;
    private int status;
    private int createTime;
    private int expiredTime;
    private double discount;
    private String keyName;
    private String dataValue;

    private boolean isChiose;

    public boolean isChiose() {
        return isChiose;
    }

    public void setChiose(boolean chiose) {
        isChiose = chiose;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
}
