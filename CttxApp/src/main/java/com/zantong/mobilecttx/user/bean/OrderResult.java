package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.router.bean.BankResponse;

/**
 * 我的订单返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class OrderResult extends BankResponse {

    private OrderBean RspInfo;

    public OrderBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(OrderBean rspInfo) {
        RspInfo = rspInfo;
    }
}
