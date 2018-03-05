package com.zantong.mobilecttx.base.bean;


import com.tzly.ctcyh.java.response.BaseResponse;

public class PayWeixinResponse extends BaseResponse {

    private PayWeixinBean data;

    public void setData(PayWeixinBean data) {
        this.data = data;
    }

    public PayWeixinBean getData() {
        return data;
    }
}
