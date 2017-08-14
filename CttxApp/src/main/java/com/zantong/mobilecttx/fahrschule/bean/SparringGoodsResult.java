package com.zantong.mobilecttx.fahrschule.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

import cn.qqtheme.framework.contract.bean.SubjectGoodsData;

/**
 * 科目强化商品列表
 */
public class SparringGoodsResult extends BaseResult {

    private SubjectGoodsData data;

    public SubjectGoodsData getData() {
        return data;
    }

    public void setData(SubjectGoodsData data) {
        this.data = data;
    }
}
