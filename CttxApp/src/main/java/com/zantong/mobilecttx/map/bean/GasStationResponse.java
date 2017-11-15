package com.zantong.mobilecttx.map.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class GasStationResponse extends BaseResponse {
    private List<GasStation> data;

    public List<GasStation> getData() {
        return data;
    }

    public void setData(List<GasStation> data) {
        this.data = data;
    }
}
