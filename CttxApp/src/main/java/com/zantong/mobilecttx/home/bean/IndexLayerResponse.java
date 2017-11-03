package com.zantong.mobilecttx.home.bean;

import cn.qqtheme.framework.bean.BaseResponse;

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
