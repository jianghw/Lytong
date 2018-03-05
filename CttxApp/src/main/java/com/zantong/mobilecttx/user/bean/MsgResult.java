package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.java.response.BankResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class MsgResult extends BankResponse {

    private Msg RspInfo;

    public Msg getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(Msg rspInfo) {
        RspInfo = rspInfo;
    }
}
