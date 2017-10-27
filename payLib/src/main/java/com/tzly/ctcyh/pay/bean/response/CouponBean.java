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
    /**
     * 参数名	类型	说明
     * id	int	用户优惠券id
     * couponValidity_end	string	优惠券截止日
     * couponImage	string	优惠券图片
     * couponName	string	优惠券名称
     * couponType	int	优惠券类型：1 无；2 折扣；3 代金券
     * couponUse	string	优惠券使用说明
     * couponCode	string	优惠券编码
     * couponContent	string	优惠券内容
     * couponValue	int	优惠券的值：如果类型是折扣则为折扣值，如果是代金券则为金额
     * couponLimit	int	优惠券使用限制条件，比如满100方可使用，为0表示不限制
     */
    private int id;
    private String couponValidityEnd;
    private String couponImage;
    private String couponName;
    private int couponType;
    private String couponUse;
    private String couponCode;
    private String couponContent;
    private int couponValue;
    private int couponLimit;

    private boolean isChoice;

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

    public int getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(int couponLimit) {
        this.couponLimit = couponLimit;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.couponValidityEnd);
        dest.writeString(this.couponImage);
        dest.writeString(this.couponName);
        dest.writeInt(this.couponType);
        dest.writeString(this.couponUse);
        dest.writeString(this.couponCode);
        dest.writeString(this.couponContent);
        dest.writeInt(this.couponValue);
        dest.writeInt(this.couponLimit);
        dest.writeByte(this.isChoice ? (byte) 1 : (byte) 0);
    }

    public CouponBean() {}

    protected CouponBean(Parcel in) {
        this.id = in.readInt();
        this.couponValidityEnd = in.readString();
        this.couponImage = in.readString();
        this.couponName = in.readString();
        this.couponType = in.readInt();
        this.couponUse = in.readString();
        this.couponCode = in.readString();
        this.couponContent = in.readString();
        this.couponValue = in.readInt();
        this.couponLimit = in.readInt();
        this.isChoice = in.readByte() != 0;
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel source) {return new CouponBean(source);}

        @Override
        public CouponBean[] newArray(int size) {return new CouponBean[size];}
    };
}
