package com.zantong.mobilecttx.car.bean;

import java.io.Serializable;

/**
 * Created by bpncool on 2/23/2016.
 */
public class CarStyleInfoBean implements Serializable{

    private final String carModelName;
    private final int carModelId;

    public CarStyleInfoBean( int id, String name) {
        this.carModelName = name;
        this.carModelId = id;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public int getCarModelId() {
        return carModelId;
    }
}
