package com.zantong.mobilecttx.chongzhi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianghw on 2017/6/5.
 * Description:
 * Update by:
 * Update day:
 */

public class RechargeCouponBean implements Parcelable {

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

    private boolean isChoice;

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChoice ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.couponValidityEnd);
        dest.writeString(this.couponImage);
        dest.writeString(this.couponName);
        dest.writeInt(this.couponType);
        dest.writeString(this.couponUse);
        dest.writeString(this.couponCode);
        dest.writeString(this.couponContent);
        dest.writeInt(this.couponValue);
    }

    public RechargeCouponBean() {
    }

    protected RechargeCouponBean(Parcel in) {
        this.isChoice = in.readByte() != 0;
        this.id = in.readInt();
        this.couponValidityEnd = in.readString();
        this.couponImage = in.readString();
        this.couponName = in.readString();
        this.couponType = in.readInt();
        this.couponUse = in.readString();
        this.couponCode = in.readString();
        this.couponContent = in.readString();
        this.couponValue = in.readInt();
    }

    public static final Parcelable.Creator<RechargeCouponBean> CREATOR = new Parcelable.Creator<RechargeCouponBean>() {
        @Override
        public RechargeCouponBean createFromParcel(Parcel source) {
            return new RechargeCouponBean(source);
        }

        @Override
        public RechargeCouponBean[] newArray(int size) {
            return new RechargeCouponBean[size];
        }
    };
}
