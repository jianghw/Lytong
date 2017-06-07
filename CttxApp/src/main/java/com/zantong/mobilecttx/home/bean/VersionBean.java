package com.zantong.mobilecttx.home.bean;

/**
 * 作者：王海洋
 * 时间：2016/8/19 16:02
 */
public class VersionBean {

    /**
     * version : 0.0.1
     * isUpdate : 0
     * address : http://192.9.210.176:8080/NGOSS_Front/download/icbcorg.txt
     */

    private String version;
    private String isUpdate;
    private String address;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
