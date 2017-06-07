package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.Result;

/**
 * 我的订单状态返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class CheckOrderResult extends Result {

    private CheckOrder RspInfo;

    public CheckOrder getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(CheckOrder rspInfo) {
        RspInfo = rspInfo;
    }
}
