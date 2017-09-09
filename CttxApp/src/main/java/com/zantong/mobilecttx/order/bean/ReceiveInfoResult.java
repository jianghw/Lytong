package com.zantong.mobilecttx.order.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

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
