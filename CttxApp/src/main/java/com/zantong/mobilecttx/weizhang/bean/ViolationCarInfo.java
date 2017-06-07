package com.zantong.mobilecttx.weizhang.bean;

import java.util.List;

/**
 * Created by zhengyingbing on 17/5/5.
 */

public class ViolationCarInfo {

    private String carnum;
    private String enginenum;
    private String totalPrice;
    public boolean isExpanded;

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getEnginenum() {
        return enginenum;
    }

    public void setEnginenum(String enginenum) {
        this.enginenum = enginenum;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public ViolationCarInfo(String carnum, String enginenum, String totalPrice, boolean isExpanded) {
        this.carnum = carnum;
        this.enginenum = enginenum;
        this.totalPrice = totalPrice;
        this.isExpanded = false;
    }
    public ViolationCarInfo(){

    }
}
