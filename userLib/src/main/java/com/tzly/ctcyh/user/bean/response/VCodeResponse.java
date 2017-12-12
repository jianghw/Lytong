package com.tzly.ctcyh.user.bean.response;


import com.tzly.ctcyh.user.bean.BankResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class VCodeResponse extends BankResponse {
    private VCodeBean RspInfo;

    public VCodeBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(VCodeBean rspInfo) {
        RspInfo = rspInfo;
    }
}
