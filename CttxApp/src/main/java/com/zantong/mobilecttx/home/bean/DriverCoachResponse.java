package com.zantong.mobilecttx.home.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * 判断是否为司机
 */

public class DriverCoachResponse extends BaseResponse {

    private boolean data;

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean getData() {
        return data;
    }
}
