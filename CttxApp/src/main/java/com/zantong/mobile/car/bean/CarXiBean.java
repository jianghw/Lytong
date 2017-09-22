package com.zantong.mobile.car.bean;

import java.io.Serializable;

/**
 * Created by bpncool on 2/23/2016.
 */
public class CarXiBean implements Serializable{

    private final String seriesName;
    private final int seriesId;

    public CarXiBean(int id, String name) {
        this.seriesName = name;
        this.seriesId = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public int getSeriesId() {
        return seriesId;
    }
}
