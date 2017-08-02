package com.zantong.mobilecttx.fahrschule.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.List;

/**
 * 科目强化商品列表
 */
public class SubjectGoodsResult extends BaseResult {

    private List<SubjectGoodsBean> data;

    public void setData(List<SubjectGoodsBean> data) {
        this.data = data;
    }

    public List<SubjectGoodsBean> getData() {
        return data;
    }
}
