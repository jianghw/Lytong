package com.zantong.mobilecttx.car.bean;

import java.io.Serializable;

/**
 * Created by zhoujie on 2016/11/14.
 */

public class CarBrandBean implements Serializable {
    private int id;
    private String brandName;
    private String brandPinYin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandPinYin() {
        return brandPinYin;
    }

    public void setBrandPinYin(String brandPinYin) {
        this.brandPinYin = brandPinYin;
    }
}
