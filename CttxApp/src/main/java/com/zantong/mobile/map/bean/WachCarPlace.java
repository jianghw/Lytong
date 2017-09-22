package com.zantong.mobile.map.bean;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class WachCarPlace {
    private int id;             //洗车地点ID
    private String name;        //洗车地点名称
    private String businessDate;//洗车地点营业时间
    private String discount;    //优惠预留字段
    private double price;	    //价格
    private double lng;         //经度
    private double lat;         //纬度

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
