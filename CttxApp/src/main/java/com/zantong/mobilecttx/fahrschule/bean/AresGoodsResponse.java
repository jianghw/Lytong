package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 4.获取区域商品列表
 */
public class AresGoodsResponse extends BaseResponse {

    private List<AresGoodsBean> data;

    public void setData(List<AresGoodsBean> data) {
        this.data = data;
    }

    public List<AresGoodsBean> getData() {
        return data;
    }
}
