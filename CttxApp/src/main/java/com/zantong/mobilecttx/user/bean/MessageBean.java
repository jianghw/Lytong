package com.zantong.mobilecttx.user.bean;

import java.util.List;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体对象
 */
public class MessageBean {

    private List<Meg> messageDetailList;

    public void setMessageDetailList(List<Meg> messageDetailList) {
        this.messageDetailList = messageDetailList;
    }

    public List<Meg> getMessageDetailList() {
        return messageDetailList;
    }
}
