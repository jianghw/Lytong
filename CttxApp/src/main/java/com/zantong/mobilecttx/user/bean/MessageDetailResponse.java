package com.zantong.mobilecttx.user.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhengyingbing on 16/6/1.
 * 消息类别返回实体列表
 */
public class MessageDetailResponse extends BaseResponse {

    private Meg data;

    public void setData(Meg data) {
        this.data = data;
    }

    public Meg getData() {
        return data;
    }
}
