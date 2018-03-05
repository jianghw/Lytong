package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体列表
 */
public class MessageTypeResponse extends BaseResponse {

    private MessageTypeBean data;

    public void setData(MessageTypeBean data) {
        this.data = data;
    }

    public MessageTypeBean getData() {
        return data;
    }
}
