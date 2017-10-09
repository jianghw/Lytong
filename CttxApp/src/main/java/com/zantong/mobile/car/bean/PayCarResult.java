package com.zantong.mobile.car.bean;

import com.tzly.annual.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayCarResult extends Result {

    private PayCarBean RspInfo;//总笔数

    public PayCarBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(PayCarBean rspInfo) {
        RspInfo = rspInfo;
    }
}
