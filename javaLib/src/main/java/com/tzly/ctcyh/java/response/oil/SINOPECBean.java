package com.tzly.ctcyh.java.response.oil;

/**
 * Created by jianghw on 18-3-27.
 */
public class SINOPECBean {
    /**
     * id : 39
     * merchantId : 14
     * name : 中石化100元在线充值
     * type : 13
     * price : 100
     * description : 本服务为全国加油卡代充服务
     * status : 1
     * createTime : 0
     * expiredTime : 86400
     * discount : 0.995
     * settlementPrice : 0
     */

    private String id;
    private int merchantId;
    private String name;
    private String type;
    private String price;
    private String description;
    private int status;
    private int createTime;
    private int expiredTime;
    private String discount;
    private int settlementPrice;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private boolean select;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(int settlementPrice) {
        this.settlementPrice = settlementPrice;
    }
}
