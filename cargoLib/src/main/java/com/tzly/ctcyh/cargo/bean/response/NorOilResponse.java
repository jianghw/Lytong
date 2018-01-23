package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.cargo.bean.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 */

public class NorOilResponse extends BaseResponse {

    private NorOilBean data;

    public void setData(NorOilBean data) {
        this.data = data;
    }

    public NorOilBean getData() {
        return data;
    }
}
