package com.tzly.ctcyh.user.bean;

/**
 * Created by jianghw on 2017/10/24.
 * Description: 同赞利盈 接口响应
 * Update by:
 * Update day:
 */

public class BaseResponse {
    private int responseCode;
    private String responseDesc;

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
