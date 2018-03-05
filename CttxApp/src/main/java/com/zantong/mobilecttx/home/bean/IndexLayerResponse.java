package com.zantong.mobilecttx.home.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 首页返回实体
 */
public class IndexLayerResponse extends BaseResponse {
    private IndexLayerBean data;

    public IndexLayerBean getData() {
        return data;
    }

    public void setData(IndexLayerBean data) {
        this.data = data;
    }
}
