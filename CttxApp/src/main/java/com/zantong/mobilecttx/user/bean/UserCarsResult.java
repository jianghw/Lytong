package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.router.bean.BankResponse;

/**
 * 我的车辆返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class UserCarsResult extends BankResponse {

    private UserCarsBean RspInfo;//我的车辆

    public UserCarsBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(UserCarsBean rspInfo) {
        RspInfo = rspInfo;
    }
}
