package com.zantong.mobilecttx.home.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by zhoujie on 2016/9/19.
 * 首页返回实体
 */
public class HomeResponse extends BaseResponse {
    private HomeBean data;

    public HomeBean getData() {
        return data;
    }

    public void setData(HomeBean data) {
        this.data = data;
    }
}
