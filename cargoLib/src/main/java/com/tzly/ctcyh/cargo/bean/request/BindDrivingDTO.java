package com.tzly.ctcyh.cargo.bean.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jianghw on 2017/11/30.
 * Description:
 * Update by:
 * Update day:
 */

public class BindDrivingDTO {
    @SerializedName("usrnum")
    private String userId;//关联用户ID
    private String name;//驾驶证姓名
    private String licenseno;//证号
    private String sex;//性别
    private String nationality;//国籍(1-中国,2-其他):
    private String address;//住址
    private String dateOfBirth;//出生日期
    private String dateOfFirstIssue;//初次领证日期
    private String allowType;//准驾车型
    private String validPeriodStart;//有效期限起始时间
    private String validPeriodEnd;//有效期限截止时间
    private String fileNum;//档案编号
    private String record;//记录
    private String memo;//备注

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseno() {
        return licenseno;
    }

    public void setLicenseno(String licenseno) {
        this.licenseno = licenseno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfFirstIssue() {
        return dateOfFirstIssue;
    }

    public void setDateOfFirstIssue(String dateOfFirstIssue) {
        this.dateOfFirstIssue = dateOfFirstIssue;
    }

    public String getAllowType() {
        return allowType;
    }

    public void setAllowType(String allowType) {
        this.allowType = allowType;
    }

    public String getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(String validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
    }

    public String getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(String validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
