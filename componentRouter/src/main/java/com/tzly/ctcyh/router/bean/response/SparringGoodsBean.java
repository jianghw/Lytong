package com.tzly.ctcyh.router.bean.response;

/**
 * Created by jianghw on 2017/8/2.
 * Description:
 * Update by:
 * Update day:
 */

public class SparringGoodsBean {


    /**
     * goodsId : 16
     * merchantId : 5
     * name : 自备轿车
     * type : 5
     * price : 108
     * description : 课程介绍课程介绍\n课程介绍课程介绍
     * status : 1
     * createTime : 1499756090
     * expiredTime : 86400
     * discount : 1
     * keyName : null
     * dataValue : null
     */

    private int goodsId;
    private int merchantId;
    private String name;
    private int type;
    private int price;
    private String description;
    private int status;
    private int createTime;
    private int expiredTime;
    private int discount;
    private Object keyName;
    private Object dataValue;

    private boolean isChoice;

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public SparringGoodsBean(String name, boolean b) {
        this.name = name;
        this.isChoice = b;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Object getKeyName() {
        return keyName;
    }

    public void setKeyName(Object keyName) {
        this.keyName = keyName;
    }

    public Object getDataValue() {
        return dataValue;
    }

    public void setDataValue(Object dataValue) {
        this.dataValue = dataValue;
    }
}
