package com.zantong.mobilecttx.map.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class WachCarPlaceResult extends BaseResult {
    private List<WachCarPlace> data;

    public List<WachCarPlace> getData() {
        return data;
    }

    public void setData(List<WachCarPlace> data) {
        this.data = data;
    }
}
