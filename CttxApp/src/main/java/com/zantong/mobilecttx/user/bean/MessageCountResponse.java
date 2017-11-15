package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体对象
 */
public class MessageCountResponse extends BaseResponse {

    private MessageCountBean data;

    public void setData(MessageCountBean data) {
        this.data = data;
    }

    public MessageCountBean getData() {
        return data;
    }
}
