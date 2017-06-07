package com.zantong.mobilecttx.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 优惠券
 */

public class CouponFragmentBean implements Parcelable {
    /**
     * couponId : 2
     * couponName : 分享赢蛋糕
     * couponContent : 分享就有蛋糕
     * couponValidityEnd : 2017-04-27
     * couponStatus : 1
     * couponImage : http://139.196.183.121:8011/admin/uploads/20170427/20170427173451882.jpeg
     * couponCode : NJ6ZAURI4F7W6IHG
     * couponUse : 123
     */

    private String couponId;
    private String couponName;
    private String couponContent;
    private String couponValidityEnd;
    private String couponStatus;
    private String couponImage;
    private String couponCode;
    private String couponUse;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public String getCouponValidityEnd() {
        return couponValidityEnd;
    }

    public void setCouponValidityEnd(String couponValidityEnd) {
        this.couponValidityEnd = couponValidityEnd;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(String couponImage) {
        this.couponImage = couponImage;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponUse() {
        return couponUse;
    }

    public void setCouponUse(String couponUse) {
        this.couponUse = couponUse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.couponId);
        dest.writeString(this.couponName);
        dest.writeString(this.couponContent);
        dest.writeString(this.couponValidityEnd);
        dest.writeString(this.couponStatus);
        dest.writeString(this.couponImage);
        dest.writeString(this.couponCode);
        dest.writeString(this.couponUse);
    }

    public CouponFragmentBean() {
    }

    protected CouponFragmentBean(Parcel in) {
        this.couponId = in.readString();
        this.couponName = in.readString();
        this.couponContent = in.readString();
        this.couponValidityEnd = in.readString();
        this.couponStatus = in.readString();
        this.couponImage = in.readString();
        this.couponCode = in.readString();
        this.couponUse = in.readString();
    }

    public static final Creator<CouponFragmentBean> CREATOR = new Creator<CouponFragmentBean>() {
        @Override
        public CouponFragmentBean createFromParcel(Parcel source) {
            return new CouponFragmentBean(source);
        }

        @Override
        public CouponFragmentBean[] newArray(int size) {
            return new CouponFragmentBean[size];
        }
    };
}
