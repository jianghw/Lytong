package com.zantong.mobile.car.bean;


/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayCar {
    private String carnumtype;//类型
    private String carnum;//车号
    private String enginenum;

    public String getCarnumtype() {
        return carnumtype;
    }

    public void setCarnumtype(String carnumtype) {
        this.carnumtype = carnumtype;
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
}
