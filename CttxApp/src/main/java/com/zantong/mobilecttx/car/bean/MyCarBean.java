package com.zantong.mobilecttx.car.bean;

/**
 * 违章查询-我的车辆列表
 * Created by zhengyingbing on 16/6/1.
 */
public class MyCarBean {
    private String car_name;
    private String engine_num;
    private String car_type;

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getEngine_num() {
        return engine_num;
    }

    public void setEngine_num(String engine_num) {
        this.engine_num = engine_num;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_type() {
        return car_type;
    }
}
