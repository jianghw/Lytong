package com.zantong.mobilecttx.daijia.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * Created by zhoujie on 2017/2/21.
 */

public class DaiJiaCreateResult extends BaseResult {

    private DaiJiaCreateBean data;

    public void setData(DaiJiaCreateBean data) {
        this.data = data;
    }

    public DaiJiaCreateBean getData() {
        return data;
    }
}
