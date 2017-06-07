package com.zantong.mobilecttx.weizhang.dto;

/**
 * 违章记录实体封装
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class ViolationHistoryDTO {

    String date;//时间周期（单位是月，一年传12）
    String usernum;//安盛用户ID(rsa加密)

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getUsernum() {
        return usernum;
    }
}
