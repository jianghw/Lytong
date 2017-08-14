package com.zantong.mobilecttx.order.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 优惠券
 */

public class CouponFragmentBean implements Parcelable {

    /**
     * couponId : 1623
     * couponName : 加油充值181元抵用券
     * couponContent : 1.此加油充值抵用券不可兑现，不可转赠，不参与其他优惠活动；
     * 2.此加油充值抵用券仅可在畅通车友会APP内使用；
     * 3.此加油充值抵用券使用时，充值金额不得低于抵用券金额；
     * 4.畅通车友会APP拥有本次活动最终解释权。
     * couponValidityEnd : 2017-10-11
     * couponStatus : 1
     * couponImage : http://liyingtong.com:8011/admin/uploads/20170810/20170810185543941.png
     * couponCode : RNMG21GMCFK16IYC
     * couponUse : 1.此加油充值抵用券不可兑现，不可转赠，不参与其他优惠活动；
     * 2.此加油充值抵用券仅可在畅通车友会APP内使用；
     * 3.此加油充值抵用券使用时，充值金额不得低于抵用券金额；
     * 4.畅通车友会APP拥有本次活动最终解释权。
     * couponType : 3
     * couponValue : 181
     */

    private String couponId;
    private String couponName;
    private String couponContent;
    private String couponValidityEnd;
    private String couponStatus;
    private String couponImage;
    private String couponCode;
    private String couponUse;
    private int couponType;
    private int couponValue;

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

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
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
        dest.writeInt(this.couponType);
        dest.writeInt(this.couponValue);
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
        this.couponType = in.readInt();
        this.couponValue = in.readInt();
    }

    public static final Parcelable.Creator<CouponFragmentBean> CREATOR = new Parcelable.Creator<CouponFragmentBean>() {
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
