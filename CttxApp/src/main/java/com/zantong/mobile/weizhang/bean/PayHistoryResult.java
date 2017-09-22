package com.zantong.mobile.weizhang.bean;

import com.zantong.mobile.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayHistoryResult extends Result {

    private PayHistory RspInfo;//总笔数

    public PayHistory getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(PayHistory rspInfo) {
        RspInfo = rspInfo;
    }
}
