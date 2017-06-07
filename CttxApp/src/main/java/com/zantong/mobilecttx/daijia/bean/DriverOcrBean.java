package com.zantong.mobilecttx.daijia.bean;

/**
 * Created by zhengyingbing on 16/9/13.
 */
public class DriverOcrBean {

    String Nation; //国籍
    String CardNo; //身份证号
    String Address; //门南11县干县腾高镇县HII0王省
    String Sex; //性别
    String IssueDate; //初次领证日期
    String Birthday; //生日
    String drivingType; //驾照类型
    String registerDate; //注册日期
    String Name; //姓名
    String ValidPeriod; //有效期

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getDrivingType() {
        return drivingType;
    }

    public void setDrivingType(String drivingType) {
        this.drivingType = drivingType;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValidPeriod() {
        return ValidPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        ValidPeriod = validPeriod;
    }
}
