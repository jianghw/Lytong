package com.zantong.mobilecttx.map.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
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
