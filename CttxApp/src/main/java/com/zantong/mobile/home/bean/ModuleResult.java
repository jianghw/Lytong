package com.zantong.mobile.home.bean;

import java.util.List;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 25.模块化接口
 */
public class ModuleResult extends BaseResult {

    private List<ModuleBean> data;

    public void setData(List<ModuleBean> data) {
        this.data = data;
    }

    public List<ModuleBean> getData() {
        return data;
    }
}
