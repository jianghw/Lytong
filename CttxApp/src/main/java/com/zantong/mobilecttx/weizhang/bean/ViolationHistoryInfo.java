package com.zantong.mobilecttx.weizhang.bean;

import java.util.List;

/**
 * Created by zhengyingbing on 17/5/5.
 */

public class ViolationHistoryInfo {
    private String carSize;
    private String totalPrice;
    private List<ViolationCarInfo> data;

    public String getCarSize() {
        return carSize;
    }

    public void setCarSize(String carSize) {
        this.carSize = carSize;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setData(List<ViolationCarInfo> data) {
        this.data = data;
    }

    public List<ViolationCarInfo> getData() {
        return data;
    }
}
