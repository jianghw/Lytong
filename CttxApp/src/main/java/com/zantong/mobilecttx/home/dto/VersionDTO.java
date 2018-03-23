package com.zantong.mobilecttx.home.dto;

/**
 * 应用版本号请求实体类
 */
public class VersionDTO {

    private String versionType; //手机类型

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }
}
