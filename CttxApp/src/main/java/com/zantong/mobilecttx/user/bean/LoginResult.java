package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.Result;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class LoginResult extends Result {

    private LoginInfoBean.RspInfoBean RspInfo;
//
//    public Login getRspInfo() {
//        return RspInfo;
//    }
//
//    public void setRspInfo(Login rspInfo) {
//        RspInfo = rspInfo;
//    }


    public void setRspInfo(LoginInfoBean.RspInfoBean rspInfo) {
        RspInfo = rspInfo;
    }

    public LoginInfoBean.RspInfoBean getRspInfo() {
        return RspInfo;
    }
}
