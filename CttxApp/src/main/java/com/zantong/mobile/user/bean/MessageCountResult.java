package com.zantong.mobile.user.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体对象
 */
public class MessageCountResult extends BaseResult {

    private MessageCountBean data;

    public void setData(MessageCountBean data) {
        this.data = data;
    }

    public MessageCountBean getData() {
        return data;
    }
}