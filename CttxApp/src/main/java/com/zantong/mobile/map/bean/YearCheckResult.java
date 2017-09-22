package com.zantong.mobile.map.bean;

import java.util.List;

import com.tzly.annual.base.bean.BaseResult;

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
