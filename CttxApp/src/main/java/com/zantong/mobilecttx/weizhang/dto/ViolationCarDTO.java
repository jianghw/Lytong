package com.zantong.mobilecttx.weizhang.dto;

import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import java.util.List;

/**
 * 缴费记录实体封装
 */
public class ViolationCarDTO {

    private String carnum;
    private String carnumtype;
    private String enginenum;
    private List<ViolationBean> violationInfo;

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getCarnumtype() {
        return carnumtype;
    }

    public void setCarnumtype(String carnumtype) {
        this.carnumtype = carnumtype;
    }

    public String getEnginenum() {
        return enginenum;
    }

    public void setEnginenum(String enginenum) {
        this.enginenum = enginenum;
    }

    public List<ViolationBean> getViolationInfo() {
        return violationInfo;
    }

    public void setViolationInfo(List<ViolationBean> violationInfo) {
        this.violationInfo = violationInfo;
    }
}
