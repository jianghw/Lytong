package com.tzly.annual.base.bean.response;


import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * 7.获取用户指定活动的统计总数
 */
public class StatistCountResult extends BaseResult {

    private List<StatistCountBean> data;

    public void setData(List<StatistCountBean> data) {
        this.data = data;
    }

    public List<StatistCountBean> getData() {
        return data;
    }
}
