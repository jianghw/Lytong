package com.zantong.mobilecttx.daijia.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhoujie on 2017/2/17.
 */

public class DrivingOrder implements Serializable{
    @SerializedName("order_id")
    private String id;
    @SerializedName("create_time")
    private String date;
    private String address;
    private String yhqContent;  //优惠券内容
    @SerializedName("order_state")
    private int orderStatu;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYhqContent() {
        return yhqContent;
    }

    public void setYhqContent(String yhqContent) {
        this.yhqContent = yhqContent;
    }

    public void setOrderStatu(int orderStatu) {
        this.orderStatu = orderStatu;
    }

    public int getOrderStatu() {
        return orderStatu;
    }
}
