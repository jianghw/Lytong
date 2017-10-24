package com.tzly.ctcyh.pay.bean.response;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponBean {
    private int id;
    private String couponValidityEnd;
    private String couponImage;
    private String couponName;
    private int couponType;
    private String couponUse;
    private String couponCode;
    private String couponContent;
    private int couponValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouponValidityEnd() {
        return couponValidityEnd;
    }

    public void setCouponValidityEnd(String couponValidityEnd) {
        this.couponValidityEnd = couponValidityEnd;
    }

    public String getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(String couponImage) {
        this.couponImage = couponImage;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getCouponUse() {
        return couponUse;
    }

    public void setCouponUse(String couponUse) {
        this.couponUse = couponUse;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }
}
