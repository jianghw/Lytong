package com.zantong.mobilecttx.user.bean;

import cn.qqtheme.framework.bean.BankResponse;

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
