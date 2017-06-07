package com.zantong.mobilecttx.weizhang.bean;

/**
 * Created by zhengyingbing on 16/6/7.
 */

/**
 * 违章信息
 */
public class ViolationInfo {
    String violationdate;//违章日期
    String violationnum;//违章单编号
    String carnum;//车牌号
    double violationamt;//违章金额
    int violationcent;//违章分数
    String processste;//处理状态
    String paydate;//缴费日期

    public String getViolationdate() {
        return violationdate;
    }

    public void setViolationdate(String violationdate) {
        this.violationdate = violationdate;
    }

    public String getViolationnum() {
        return violationnum;
    }

    public void setViolationnum(String violationnum) {
        this.violationnum = violationnum;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public double getViolationamt() {
        return violationamt;
    }

    public void setViolationamt(double violationamt) {
        this.violationamt = violationamt;
    }

    public int getViolationcent() {
        return violationcent;
    }

    public void setViolationcent(int violationcent) {
        this.violationcent = violationcent;
    }

    public String getProcessste() {
        return processste;
    }

    public void setProcessste(String processste) {
        this.processste = processste;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }
}
