package com.zantong.mobilecttx.fahrschule.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

import java.util.List;

/**
 * 20.新手陪练获取服务地区
 */
public class SparringAreaResult extends BaseResult {

    private List<SparringAreaBean> data;

    public void setData(List<SparringAreaBean> data) {
        this.data = data;
    }

    public List<SparringAreaBean> getData() {
        return data;
    }
}
