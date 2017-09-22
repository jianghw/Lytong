package com.zantong.mobile.user.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体列表
 */
public class MessageTypeResult extends BaseResult {

    private MessageTypeBean data;

    public void setData(MessageTypeBean data) {
        this.data = data;
    }

    public MessageTypeBean getData() {
        return data;
    }
}
