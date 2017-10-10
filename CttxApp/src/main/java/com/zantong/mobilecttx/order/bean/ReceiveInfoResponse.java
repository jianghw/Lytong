package com.zantong.mobilecttx.order.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 * 获取收件人信息
 */
public class ReceiveInfoResponse extends BaseResponse {

    private ReceiveInfoBean data;

    public void setData(ReceiveInfoBean data) {
        this.data = data;
    }

    public ReceiveInfoBean getData() {
        return data;
    }
}
