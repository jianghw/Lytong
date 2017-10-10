package com.zantong.mobilecttx.weizhang.bean;

import cn.qqtheme.framework.bean.BankResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayHistoryResult extends BankResponse {

    private PayHistory RspInfo;//总笔数

    public PayHistory getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(PayHistory rspInfo) {
        RspInfo = rspInfo;
    }
}
