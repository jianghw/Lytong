package com.zantong.mobilecttx.user.bean;

import java.util.List;

/**
 * 我的订单javabean
 * Created by zhengyingbing on 16/6/1.
 */
public class OrderBean{

    private int totalrownum;
    private int totalpagenum;
    private int nowpagenum;
    private List<OrderItem> OrderInfo;


    public int getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(int totalrownum) {
        this.totalrownum = totalrownum;
    }

    public int getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(int totalpagenum) {
        this.totalpagenum = totalpagenum;
    }

    public int getNowpagenum() {
        return nowpagenum;
    }

    public void setNowpagenum(int nowpagenum) {
        this.nowpagenum = nowpagenum;
    }

    public List<OrderItem> getOrderInfo() {
        return OrderInfo;
    }

    public void setOrderInfo(List<OrderItem> orderInfo) {
        OrderInfo = orderInfo;
    }
}
