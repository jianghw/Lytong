package com.tzly.ctcyh.pay.bean.response;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponStatusBean {

    private boolean isEnable;

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    /**
     * couponUserId : 438
     * couponName : 年检
     * couponValidityEnd : 2017.11.11
     * couponValidityStart : 2017.04.26
     * couponImage : 烦烦烦
     * couponUse : 123
     * couponType : 2
     * couponValue : 85
     * couponLimit : 100
     * couponBusiness : 年检
     * channel : 0
     * method : 0
     */

    private String couponUserId;
    private String couponName;
    private String couponValidityEnd;
    private String couponValidityStart;
    private String couponImage;
    private String couponUse;
    private String couponType;
    private String couponValue;
    private String couponLimit;
    private String couponBusiness;
    private String channel;

    private String method;

    public String getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(String couponUserId) {
        this.couponUserId = couponUserId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponValidityEnd() {
        return couponValidityEnd;
    }

    public void setCouponValidityEnd(String couponValidityEnd) {
        this.couponValidityEnd = couponValidityEnd;
    }

    public String getCouponValidityStart() {
        return couponValidityStart;
    }

    public void setCouponValidityStart(String couponValidityStart) {
        this.couponValidityStart = couponValidityStart;
    }

    public String getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(String couponImage) {
        this.couponImage = couponImage;
    }

    public String getCouponUse() {
        return couponUse;
    }

    public void setCouponUse(String couponUse) {
        this.couponUse = couponUse;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    public String getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(String couponLimit) {
        this.couponLimit = couponLimit;
    }

    public String getCouponBusiness() {
        return couponBusiness;
    }

    public void setCouponBusiness(String couponBusiness) {
        this.couponBusiness = couponBusiness;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
