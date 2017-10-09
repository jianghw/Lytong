package com.zantong.mobile.user.bean;

import com.tzly.annual.base.bean.Result;

/**
 * 我的订单返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class OrderResult extends Result {

    private OrderBean RspInfo;

    public OrderBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(OrderBean rspInfo) {
        RspInfo = rspInfo;
    }
}
