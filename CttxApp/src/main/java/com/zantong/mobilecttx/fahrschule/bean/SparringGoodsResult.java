package com.zantong.mobilecttx.fahrschule.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.List;

import cn.qqtheme.framework.contract.bean.SparringGoodsBean;

/**
 * 科目强化商品列表
 */
public class SparringGoodsResult extends BaseResult {

    private List<SparringGoodsBean> data;

    public void setData(List<SparringGoodsBean> data) {
        this.data = data;
    }

    public List<SparringGoodsBean> getData() {
        return data;
    }
}
