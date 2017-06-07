package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class MsgResult extends Result {

    private Msg RspInfo;

    public Msg getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(Msg rspInfo) {
        RspInfo = rspInfo;
    }
}
