package com.zantong.mobilecttx.map.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class GasStationDetailResult extends BaseResult {
    private GasStationDetail data;

    public GasStationDetail getData() {
        return data;
    }

    public void setData(GasStationDetail data) {
        this.data = data;
    }
}
