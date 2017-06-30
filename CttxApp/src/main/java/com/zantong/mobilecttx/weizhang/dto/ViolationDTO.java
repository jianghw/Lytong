package com.zantong.mobilecttx.weizhang.dto;

import java.io.Serializable;

/**
 * Created by zhoujie on 2017/2/15.
 */

public class ViolationDTO implements Serializable {
    private String carnum;
    private String enginenum;
    private String carnumtype;
    private String processste;//0未处理，1已处理，2全部
    private String token;

//    private String version;
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }

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

    public String getCarnumtype() {
        return carnumtype;
    }

    public void setCarnumtype(String carnumtype) {
        this.carnumtype = carnumtype;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setProcessste(String processste) {
        this.processste = processste;
    }

    public String getProcessste() {
        return processste;
    }
}
