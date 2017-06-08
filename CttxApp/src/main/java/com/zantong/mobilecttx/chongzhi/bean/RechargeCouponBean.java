package com.zantong.mobilecttx.chongzhi.bean;

/**
 * Created by jianghw on 2017/6/5.
 * Description:
 * Update by:
 * Update day:
 */

public class RechargeCouponBean {


    /**
     * id : 1
     * couponValidity_end : 2017-04-26
     * couponImage : 烦烦烦
     * couponName : 地对地导弹
     * couponType : 1  优惠券类型：1 无；2 折扣；3 代金券
     * couponUse : 123
     * couponCode : null
     * couponContent : 反反复复
     * couponValue : 0
     */

    private int id;
    private String couponValidityEnd;
    private String couponImage;
    private String couponName;
    private int couponType;
    private String couponUse;
    private Object couponCode;
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

    public Object getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Object couponCode) {
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
