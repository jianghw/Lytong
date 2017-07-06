package com.zantong.mobilecttx.fahrschule.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.List;

/**
 * 4.获取区域商品列表
 */
public class AresGoodsResult extends BaseResult {

    private List<AresGoodsBean> data;

    public void setData(List<AresGoodsBean> data) {
        this.data = data;
    }

    public List<AresGoodsBean> getData() {
        return data;
    }
}
