package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.Result;

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
