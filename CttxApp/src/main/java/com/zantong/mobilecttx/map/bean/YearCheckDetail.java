package com.zantong.mobilecttx.map.bean;

/**
 * Created by zhoujie on 2016/9/19.
 * 年检详情
 */
public class YearCheckDetail {

    private int id;
    private String name;
    private String type;
    private String cityId;
    private double lng;
    private double lat;
    private String businessScope;
    private String businessTime;
    private String phoneNoOne;
    private String phoneNoTwo;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getPhoneNoOne() {
        return phoneNoOne;
    }

    public void setPhoneNoOne(String phoneNoOne) {
        this.phoneNoOne = phoneNoOne;
    }

    public String getPhoneNoTwo() {
        return phoneNoTwo;
    }

    public void setPhoneNoTwo(String phoneNoTwo) {
        this.phoneNoTwo = phoneNoTwo;
    }
}
