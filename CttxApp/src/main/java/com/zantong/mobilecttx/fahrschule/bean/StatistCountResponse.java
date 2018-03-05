package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 7.获取用户指定活动的统计总数
 */
public class StatistCountResponse extends BaseResponse {

    private List<StatistCountBean> data;

    public void setData(List<StatistCountBean> data) {
        this.data = data;
    }

    public List<StatistCountBean> getData() {
        return data;
    }
}
