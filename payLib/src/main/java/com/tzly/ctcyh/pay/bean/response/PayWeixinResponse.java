package com.tzly.ctcyh.pay.bean.response;


import com.tzly.ctcyh.pay.bean.BaseResponse;

public class PayWeixinResponse extends BaseResponse {

    private PayWeixinBean data;

    public void setData(PayWeixinBean data) {
        this.data = data;
    }

    public PayWeixinBean getData() {
        return data;
    }
}
