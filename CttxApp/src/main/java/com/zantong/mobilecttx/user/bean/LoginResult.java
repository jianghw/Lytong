package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.router.bean.BankResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class LoginResult extends BankResponse {

    private RspInfoBean RspInfo;
//
//    public Login getRspInfo() {
//        return RspInfo;
//    }
//
//    public void setRspInfo(Login rspInfo) {
//        RspInfo = rspInfo;
//    }


    public void setRspInfo(RspInfoBean rspInfo) {
        RspInfo = rspInfo;
    }

    public RspInfoBean getRspInfo() {
        return RspInfo;
    }
}
