package com.zantong.mobilecttx.user.bean;

import java.util.List;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体对象
 */
public class MessageTypeBean {

    List<MessageType> messageList;

    public void setMessageList(List<MessageType> messageList) {
        this.messageList = messageList;
    }

    public List<MessageType> getMessageList() {
        return messageList;
    }
}
