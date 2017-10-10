package com.zantong.mobilecttx.fahrschule.bean;

import cn.qqtheme.framework.bean.BaseResponse;

import cn.qqtheme.framework.bean.response.SubjectGoodsData;

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
