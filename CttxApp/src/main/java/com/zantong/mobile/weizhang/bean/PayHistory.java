package com.zantong.mobile.weizhang.bean;


import java.util.ArrayList;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayHistory {

    private int totcount;//总笔数
    private String totcarcount;//总车牌数
    private double totamt;//总金额

    private ArrayList<ViolationInfo> ViolationInfo;//违章信息

    public int getTotcount() {
        return totcount;
    }

    public void setTotcount(int totcount) {
        this.totcount = totcount;
    }

    public String getTotcarcount() {
        return totcarcount;
    }

    public void setTotcarcount(String totcarcount) {
        this.totcarcount = totcarcount;
    }

    public double getTotamt() {
        return totamt;
    }

    public void setTotamt(double totamt) {
        this.totamt = totamt;
    }

    public ArrayList<ViolationInfo> getViolationInfo() {
        return ViolationInfo;
    }

    public void setViolationInfo(ArrayList<ViolationInfo> violationInfo) {
        ViolationInfo = violationInfo;
    }
}
