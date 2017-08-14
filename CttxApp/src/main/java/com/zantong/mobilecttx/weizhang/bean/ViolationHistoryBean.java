package com.zantong.mobilecttx.weizhang.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * Created by zhengyingbing on 17/5/5.
 */

public class ViolationHistoryBean extends BaseResult{
    private ViolationHistoryInfo data;

    public void setData(ViolationHistoryInfo data) {
        this.data = data;
    }

    public ViolationHistoryInfo getData() {
        return data;
    }
}
