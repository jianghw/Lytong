package com.tzly.ctcyh.pay.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianghw on 2017/10/24.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponBean implements Parcelable {

    private boolean isChoice;

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    /**
     * couponImage : 烦烦烦
     * couponUse : 123
     * couponName : 年检
     * method : 0
     * couponContent : 年检
     * channel : 0
     * couponLimit : 1
     * couponId : 1
     * couponValue : 2
     * receiveTime : null
     * couponUserId : 738
     * couponType : 2
     * couponCode : 898I96AAL448LQRK
     * couponValidityEnd : 2018-11-11
     */

    private String couponImage;
    private String couponUse;
    private String couponName;
    private int method;
    private String couponContent;
    private int channel;
    private int couponLimit;
    private int couponId;
    private int couponValue;
    private String receiveTime;
    private int couponUserId;
    private int couponType;
    private String couponCode;
    private String couponValidityEnd;

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

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(int couponLimit) {
        this.couponLimit = couponLimit;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(int couponUserId) {
        this.couponUserId = couponUserId;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponValidityEnd() {
        return couponValidityEnd;
    }

    public void setCouponValidityEnd(String couponValidityEnd) {
        this.couponValidityEnd = couponValidityEnd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChoice ? (byte) 1 : (byte) 0);
        dest.writeString(this.couponImage);
        dest.writeString(this.couponUse);
        dest.writeString(this.couponName);
        dest.writeInt(this.method);
        dest.writeString(this.couponContent);
        dest.writeInt(this.channel);
        dest.writeInt(this.couponLimit);
        dest.writeInt(this.couponId);
        dest.writeInt(this.couponValue);
        dest.writeString(this.receiveTime);
        dest.writeInt(this.couponUserId);
        dest.writeInt(this.couponType);
        dest.writeString(this.couponCode);
        dest.writeString(this.couponValidityEnd);
    }

    public CouponBean() {
    }

    protected CouponBean(Parcel in) {
        this.isChoice = in.readByte() != 0;
        this.couponImage = in.readString();
        this.couponUse = in.readString();
        this.couponName = in.readString();
        this.method = in.readInt();
        this.couponContent = in.readString();
        this.channel = in.readInt();
        this.couponLimit = in.readInt();
        this.couponId = in.readInt();
        this.couponValue = in.readInt();
        this.receiveTime = in.readString();
        this.couponUserId = in.readInt();
        this.couponType = in.readInt();
        this.couponCode = in.readString();
        this.couponValidityEnd = in.readString();
    }

    public static final Parcelable.Creator<CouponBean> CREATOR = new Parcelable.Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel source) {
            return new CouponBean(source);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };
}
