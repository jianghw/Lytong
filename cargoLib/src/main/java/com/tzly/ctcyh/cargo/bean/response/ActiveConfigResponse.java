package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class ActiveConfigResponse extends BaseResponse {

    private ActiveConfigBean data;

    public void setData(ActiveConfigBean data) {
        this.data = data;
    }

    public ActiveConfigBean getData() {
        return data;
    }
}
