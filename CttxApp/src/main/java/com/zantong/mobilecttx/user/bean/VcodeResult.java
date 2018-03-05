package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.java.response.BankResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class VcodeResult extends BankResponse {

    private VcodeBean RspInfo;

    public VcodeBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(VcodeBean rspInfo) {
        RspInfo = rspInfo;
    }
}
