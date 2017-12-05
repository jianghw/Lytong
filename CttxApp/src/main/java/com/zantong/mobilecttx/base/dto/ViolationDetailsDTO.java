package com.zantong.mobilecttx.base.dto;

/**
 * 驾驶证查分 请求bean
 */

public class ViolationDetailsDTO {

    private String violationnum;
    private String token;

    public String getViolationnum() {
        return violationnum;
    }

    public void setViolationnum(String violationnum) {
        this.violationnum = violationnum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
