package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 油卡 分享
 */

public class OilShareResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : 3
     */

    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
