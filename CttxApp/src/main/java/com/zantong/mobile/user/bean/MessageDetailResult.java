package com.zantong.mobile.user.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体列表
 */
public class MessageDetailResult extends BaseResult {

    private Meg data;

    public void setData(Meg data) {
        this.data = data;
    }

    public Meg getData() {
        return data;
    }
}
