package com.zantong.mobilecttx.weizhang.bean;


import cn.qqtheme.framework.bean.BankResponse;

/**
 * Created by zhengyingbing on 17/5/5.
 */

public class ViolationNumBean extends BankResponse {

    private ViolationNum RspInfo;

    public ViolationNum getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(ViolationNum rspInfo) {
        RspInfo = rspInfo;
    }
}
