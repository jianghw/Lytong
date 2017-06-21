package com.zantong.mobilecttx.home.dto;

/**
 * 应用版本号请求实体类
 *
 * @author Sandy
 *         create at 16/10/16 上午1:44
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
