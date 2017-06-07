package com.zantong.mobilecttx.car.dto;

/**
 * Created by zhoujie on 2016/9/22.
 */

public class CarManagerDTO {
    private String lng;	   //经度
    private String lat;	   //维度
    private String scope;  //范围(单位为km，不传默认为3km)
    private String type;  //类型

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
