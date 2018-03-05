package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import com.tzly.ctcyh.java.response.SubjectGoodsData;

/**
 * 科目强化商品列表
 */
public class SparringGoodsResponse extends BaseResponse {

    private SubjectGoodsData data;

    public SubjectGoodsData getData() {
        return data;
    }

    public void setData(SubjectGoodsData data) {
        this.data = data;
    }
}
