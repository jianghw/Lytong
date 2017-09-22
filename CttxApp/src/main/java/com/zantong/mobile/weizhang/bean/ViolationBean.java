package com.zantong.mobile.weizhang.bean;

/**
 * Created by zhengyingbing on 16/6/7.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 违章信息
 */
public class ViolationBean implements Parcelable {

    private String violationtime; //违章时间
    private String carnumtype; //车辆类型
    private String violationcent; //违章分数
    private int processste; //处理状态 0未处理 1已处理
    private String carnum; //车牌
    private String violationdate; //违章日期
    private String violationamt; //违章金额
    private String violationnum; //为账单编号
    private String violationplace; //违章地点
    private String violationinfo; //违章条例
    private String violationtype; //违章事由

    public String getViolationtime() {
        return violationtime;
    }

    public void setViolationtime(String violationtime) {
        this.violationtime = violationtime;
    }

    public String getCarnumtype() {
        return carnumtype;
    }

    public void setCarnumtype(String carnumtype) {
        this.carnumtype = carnumtype;
    }

    public String getViolationcent() {
        return violationcent;
    }

    public void setViolationcent(String violationcent) {
        this.violationcent = violationcent;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getViolationdate() {
        return violationdate;
    }

    public void setViolationdate(String violationdate) {
        this.violationdate = violationdate;
    }

    public String getViolationamt() {
        return violationamt;
    }

    public void setViolationamt(String violationamt) {
        this.violationamt = violationamt;
    }

    public String getViolationnum() {
        return violationnum;
    }

    public void setViolationnum(String violationnum) {
        this.violationnum = violationnum;
    }

    public String getViolationplace() {
        return violationplace;
    }

    public void setViolationplace(String violationplace) {
        this.violationplace = violationplace;
    }

    public String getViolationinfo() {
        return violationinfo;
    }

    public void setViolationinfo(String violationinfo) {
        this.violationinfo = violationinfo;
    }

    public String getViolationtype() {
        return violationtype;
    }

    public void setViolationtype(String violationtype) {
        this.violationtype = violationtype;
    }

    public void setProcessste(int processste) {
        this.processste = processste;
    }

    public int getProcessste() {
        return processste;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.violationtime);
        dest.writeString(this.carnumtype);
        dest.writeString(this.violationcent);
        dest.writeInt(this.processste);
        dest.writeString(this.carnum);
        dest.writeString(this.violationdate);
        dest.writeString(this.violationamt);
        dest.writeString(this.violationnum);
        dest.writeString(this.violationplace);
        dest.writeString(this.violationinfo);
        dest.writeString(this.violationtype);
    }

    public ViolationBean() {
    }

    protected ViolationBean(Parcel in) {
        this.violationtime = in.readString();
        this.carnumtype = in.readString();
        this.violationcent = in.readString();
        this.processste = in.readInt();
        this.carnum = in.readString();
        this.violationdate = in.readString();
        this.violationamt = in.readString();
        this.violationnum = in.readString();
        this.violationplace = in.readString();
        this.violationinfo = in.readString();
        this.violationtype = in.readString();
    }

    public static final Parcelable.Creator<ViolationBean> CREATOR = new Parcelable.Creator<ViolationBean>() {
        @Override
        public ViolationBean createFromParcel(Parcel source) {
            return new ViolationBean(source);
        }

        @Override
        public ViolationBean[] newArray(int size) {
            return new ViolationBean[size];
        }
    };
}
