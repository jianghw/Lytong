package com.zantong.mobilecttx.base.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by jianghw on 17-12-18.
 */

public class ValidCountResponse extends BaseResponse {

    private ValidCountBean data;

    public ValidCountBean getData() {
        return data;
    }

    public void setData(ValidCountBean data) {
        this.data = data;
    }
}
