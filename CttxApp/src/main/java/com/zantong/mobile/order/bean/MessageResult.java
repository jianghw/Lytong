package com.zantong.mobile.order.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体列表
 */
public class MessageResult extends BaseResult {

    private MessageBean data;

    public void setData(MessageBean data) {
        this.data = data;
    }

    public MessageBean getData() {
        return data;
    }
}
