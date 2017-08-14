package com.zantong.mobilecttx.home.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * 判断是否为司机
 */

public class DriverCoachResult extends BaseResult {

    private boolean data;

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean getData() {
        return data;
    }
}
