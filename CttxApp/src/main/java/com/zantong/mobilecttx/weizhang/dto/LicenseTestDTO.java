package com.zantong.mobilecttx.weizhang.dto;

/**
 * 驾驶证查分 请求bean
 */

public class LicenseTestDTO {

    private String violationnum;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getViolationnum() {
        return violationnum;
    }

    public void setViolationnum(String violationnum) {
        this.violationnum = violationnum;
    }
}
