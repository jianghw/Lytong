package com.zantong.mobilecttx.order.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体列表
 */
public class MessageResponse extends BaseResponse {

    private MessageBean data;

    public void setData(MessageBean data) {
        this.data = data;
    }

    public MessageBean getData() {
        return data;
    }
}
