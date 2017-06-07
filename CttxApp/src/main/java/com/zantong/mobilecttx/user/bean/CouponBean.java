package com.zantong.mobilecttx.user.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhoujie on 2017/2/13.
 */

public class CouponBean {
    @SerializedName("couponId")
    private String id;
    @SerializedName("couponImage")
    private String url;
    @SerializedName("couponName")
    private String couponName;
    @SerializedName("couponContent")
    private String couponContent;
    @SerializedName("couponValidityEnd")
    private String couponDate;
    @SerializedName("couponStatus")
    private String stuta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDate() {
        return couponDate;
    }

    public void setCouponDate(String couponDate) {
        this.couponDate = couponDate;
    }

    public String getStuta() {
        return stuta;
    }

    public void setStuta(String stuta) {
        this.stuta = stuta;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }
}
