package com.zantong.mobilecttx.map.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class GasStationDetailResponse extends BaseResponse {
    private GasStationDetail data;

    public GasStationDetail getData() {
        return data;
    }

    public void setData(GasStationDetail data) {
        this.data = data;
    }
}
