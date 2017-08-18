package com.zantong.mobilecttx.map.bean;

import java.util.List;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * 年检
 */
public class YearCheckResult extends BaseResult {
    private List<YearCheck> data;

    public List<YearCheck> getData() {
        return data;
    }

    public void setData(List<YearCheck> data) {
        this.data = data;
    }
}
