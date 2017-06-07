package com.zantong.mobilecttx.map.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

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
