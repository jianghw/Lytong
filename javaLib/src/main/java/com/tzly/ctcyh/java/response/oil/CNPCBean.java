package com.tzly.ctcyh.java.response.oil;

/**
 * Created by jianghw on 18-3-27.
 */
public class CNPCBean {
    /**
     * id : 36
     * merchantId : 13
     * name : 中石油100元在线充值
     * type : 13
     * price : 100
     * description : 本服务为全国加油卡代充服务
     * status : 1
     * createTime : 0
     * expiredTime : 86400
     * discount : 1
     * settlementPrice : 0
     */

    private int id;
    private int merchantId;
    private String name;
    private int type;
    private int price;
    private String description;
    private int status;
    private int createTime;
    private int expiredTime;
    private int discount;
    private int settlementPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(int settlementPrice) {
        this.settlementPrice = settlementPrice;
    }
}
