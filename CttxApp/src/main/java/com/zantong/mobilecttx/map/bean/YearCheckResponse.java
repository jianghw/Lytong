package com.zantong.mobilecttx.map.bean;

import java.util.List;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * 年检
 */
public class YearCheckResponse extends BaseResponse {
    private List<YearCheck> data;

    public List<YearCheck> getData() {
        return data;
    }

    public void setData(List<YearCheck> data) {
        this.data = data;
    }
}
