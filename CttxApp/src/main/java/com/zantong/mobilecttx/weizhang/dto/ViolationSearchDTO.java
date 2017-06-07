package com.zantong.mobilecttx.weizhang.dto;

/**
 * 获取指定车辆违章缴费记录
 * @author Sandy
 * create at 16/6/1 下午7:34
 */
public class ViolationSearchDTO {

    String date;//时间周期（单位是月，一年传12）
    String usernum;//安盛用户ID(rsa加密)
    String carnum;//车牌号

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

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getCarnum() {
        return carnum;
    }
}
