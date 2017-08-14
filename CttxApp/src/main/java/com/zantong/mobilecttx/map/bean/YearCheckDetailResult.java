package com.zantong.mobilecttx.map.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class YearCheckDetailResult extends BaseResult {
    private YearCheckDetail data;

    public YearCheckDetail getData() {
        return data;
    }

    public void setData(YearCheckDetail data) {
        this.data = data;
    }
}
