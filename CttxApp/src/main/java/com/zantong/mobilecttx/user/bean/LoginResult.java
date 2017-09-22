package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class LoginResult extends Result {

    private RspInfoBean RspInfo;

    public void setRspInfo(RspInfoBean rspInfo) {
        RspInfo = rspInfo;
    }

    public RspInfoBean getRspInfo() {
        return RspInfo;
    }
}
