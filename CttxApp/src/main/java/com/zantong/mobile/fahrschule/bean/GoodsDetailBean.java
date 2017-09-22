package com.zantong.mobile.fahrschule.bean;

/**
 * Created by jianghw on 2017/7/12.
 * Description:
 * Update by:
 * Update day:
 */

public class GoodsDetailBean {

    /**
     * goodsId : 3
     * name : 11111
     * price : 1111
     * description : 1111111111
     * type : 3
     * courseAddress : 111111111
     * traffic : 11111111
     */

    private int goodsId;
    private String name;
    private int price;
    private String description;
    private int type;
    private String courseAddress;
    private String traffic;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCourseAddress() {
        return courseAddress;
    }

    public void setCourseAddress(String courseAddress) {
        this.courseAddress = courseAddress;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }
}
