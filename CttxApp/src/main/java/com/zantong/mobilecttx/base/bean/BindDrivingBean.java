package com.zantong.mobilecttx.base.bean;

/**
 * Name   姓名
 * Num    身份证号
 * Sex     性别
 * Birt     生日
 * Addr    地址
 * Issue    初次领证日期
 * ValidPeriod 有效期
 * Nation   国籍
 * DrivingType  准驾车型
 * RegisterDate 有效起始日期
 */

public class BindDrivingBean {
    private String Name;
    private String Num;
    private String Sex;
    private String Birt;
    private String Addr;
    private String Issue;
    private String ValidPeriod;
    private String Nation;
    private String DrivingType;
    private String RegisterDate;

    public String getName() {
        return Name;
    }

    public String getNum() {
        return Num;
    }

    public String getSex() {
        return Sex;
    }

    public String getBirt() {
        return Birt;
    }

    public String getAddr() {
        return Addr;
    }

    public String getIssue() {
        return Issue;
    }

    public String getValidPeriod() {
        return ValidPeriod;
    }

    public String getNation() {
        return Nation;
    }

    public String getDrivingType() {
        return DrivingType;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }
}
