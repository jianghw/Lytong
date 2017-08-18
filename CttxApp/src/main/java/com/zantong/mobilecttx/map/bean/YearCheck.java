package com.zantong.mobilecttx.map.bean;

/**
 * 年检
 */
public class YearCheck {
    private int id;                 //年检地点ID
    private String name;         //年检地点名称
    private String businessDate; //年检地点营业时间
    private double lng;             //经度
    private double lat;             //纬度

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
