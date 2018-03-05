package com.zantong.mobilecttx.map.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class WachCarPlaceResponse extends BaseResponse {
    private List<WachCarPlace> data;

    public List<WachCarPlace> getData() {
        return data;
    }

    public void setData(List<WachCarPlace> data) {
        this.data = data;
    }
}
