package com.zantong.mobilecttx.user.bean;

import cn.qqtheme.framework.bean.BankResponse;

/**
 * 我的订单状态返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class CheckOrderResult extends BankResponse {

    private CheckOrder RspInfo;

    public CheckOrder getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(CheckOrder rspInfo) {
        RspInfo = rspInfo;
    }
}
