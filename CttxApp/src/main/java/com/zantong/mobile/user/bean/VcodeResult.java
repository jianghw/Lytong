package com.zantong.mobile.user.bean;

import com.tzly.annual.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class VcodeResult extends Result {

    private VcodeBean RspInfo;

    public VcodeBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(VcodeBean rspInfo) {
        RspInfo = rspInfo;
    }
}
