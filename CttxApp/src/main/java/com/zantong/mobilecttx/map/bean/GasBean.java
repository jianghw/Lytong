package com.zantong.mobilecttx.map.bean;

/**
 * Created by zhengyingbing on 16/9/20.
 */

public class GasBean {
    String name;
    double longitude;//经度
    double latitude;//纬度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
