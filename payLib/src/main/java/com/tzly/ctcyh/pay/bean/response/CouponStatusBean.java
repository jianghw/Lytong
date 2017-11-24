package com.tzly.ctcyh.pay.bean.response;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponStatusBean {

    /**
     * couponId : 40
     * couponName : 满299 - 99
     * couponValidityEnd : 2017-12-31
     * couponImage : http://139.196.183.121:8011/admin/uploads/20170509/20170509180544742.jpg
     * couponType : 3
     * couponValue : 90
     * couponLimit : 0
     */

    private String couponId;
    private String couponName;
    private String couponValidityEnd;
    private String couponImage;
    private String couponType;
    private String couponValue;
    private String couponLimit;
    private String couponBusiness;

    public String getCouponBusiness() {
        return couponBusiness;
    }

    public void setCouponBusiness(String couponBusiness) {
        this.couponBusiness = couponBusiness;
    }

    public String getCouponId() { return couponId;}

    public void setCouponId(String couponId) { this.couponId = couponId;}

    public String getCouponName() { return couponName;}

    public void setCouponName(String couponName) { this.couponName = couponName;}

    public String getCouponValidityEnd() { return couponValidityEnd;}

    public void setCouponValidityEnd(String couponValidityEnd) { this.couponValidityEnd = couponValidityEnd;}

    public String getCouponImage() { return couponImage;}

    public void setCouponImage(String couponImage) { this.couponImage = couponImage;}

    public String getCouponType() { return couponType;}

    public void setCouponType(String couponType) { this.couponType = couponType;}

    public String getCouponValue() { return couponValue;}

    public void setCouponValue(String couponValue) { this.couponValue = couponValue;}

    public String getCouponLimit() { return couponLimit;}

    public void setCouponLimit(String couponLimit) { this.couponLimit = couponLimit;}

}
