package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 4.获取区域商品列表
 */
public class ServerTimeResult extends BaseResult {

    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
