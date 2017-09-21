package com.tzly.annual.base.bean;

/**
 * Description: 基础bean
 * Update by:
 * Update day:
 */
public class BaseResult {

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
