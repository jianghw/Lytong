package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * 4.获取区域商品列表
 */
public class ServerTimeResponse extends BaseResponse {

    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
