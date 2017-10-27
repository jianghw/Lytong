package com.tzly.ctcyh.user.bean.response;


import com.tzly.ctcyh.user.bean.BankResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class LoginResponse extends BankResponse {
    private LoginBean RspInfo;

    public LoginBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(LoginBean rspInfo) {
        RspInfo = rspInfo;
    }
}
