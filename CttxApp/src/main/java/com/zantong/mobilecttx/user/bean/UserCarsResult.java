package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.Result;

/**
 * 我的车辆返回实体
 * Created by zhengyingbing on 16/6/1.
 */
public class UserCarsResult extends Result {

    private UserCarsBean RspInfo;//我的车辆

    public UserCarsBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(UserCarsBean rspInfo) {
        RspInfo = rspInfo;
    }
}
