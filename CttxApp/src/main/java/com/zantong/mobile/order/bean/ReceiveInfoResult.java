package com.zantong.mobile.order.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhengyingbing on 16/6/1.
 * 获取收件人信息
 */
public class ReceiveInfoResult extends BaseResult {

    private ReceiveInfoBean data;

    public void setData(ReceiveInfoBean data) {
        this.data = data;
    }

    public ReceiveInfoBean getData() {
        return data;
    }
}
