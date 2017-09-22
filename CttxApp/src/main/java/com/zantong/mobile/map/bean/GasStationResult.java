package com.zantong.mobile.map.bean;

import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class GasStationResult extends BaseResult {
    private List<GasStation> data;

    public List<GasStation> getData() {
        return data;
    }

    public void setData(List<GasStation> data) {
        this.data = data;
    }
}
