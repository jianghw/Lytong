package com.tzly.ctcyh.router.bean;

/**
 * Description: tzly 服务器响应 base
 * Update by:
 * Update day:
 */
public class BaseResponse {
    int responseCode;
    String responseDesc;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }
}
