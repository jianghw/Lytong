package com.zantong.mobilecttx.daijia.bean;

import cn.qqtheme.framework.bean.BaseResponse;

/**
 * Created by zhoujie on 2017/2/21.
 */

public class DaiJiaCreateResponse extends BaseResponse {

    private DaiJiaCreateBean data;

    public void setData(DaiJiaCreateBean data) {
        this.data = data;
    }

    public DaiJiaCreateBean getData() {
        return data;
    }
}
