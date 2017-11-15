package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.util.List;

/**
 * 7.获取用户指定活动的统计总数
 */
public class RecordCountResponse extends BaseResponse {

    private List<RecordCountBean> data;

    public void setData(List<RecordCountBean> data) {
        this.data = data;
    }

    public List<RecordCountBean> getData() {
        return data;
    }
}
