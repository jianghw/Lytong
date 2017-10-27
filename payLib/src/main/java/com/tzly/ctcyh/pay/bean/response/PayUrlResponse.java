package com.tzly.ctcyh.pay.bean.response;


import com.tzly.ctcyh.pay.bean.BaseResponse;

public class PayUrlResponse extends BaseResponse {

    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
