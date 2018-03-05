package com.zantong.mobilecttx.car.bean;

import com.tzly.ctcyh.java.response.BankResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayCarResult extends BankResponse {

    private PayCarBean RspInfo;//总笔数

    public PayCarBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(PayCarBean rspInfo) {
        RspInfo = rspInfo;
    }
}
