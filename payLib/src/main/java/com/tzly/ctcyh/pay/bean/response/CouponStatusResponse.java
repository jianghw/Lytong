package com.tzly.ctcyh.pay.bean.response;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponStatusResponse extends BaseResponse {

    private CouponStatusList data;

    public void setData(CouponStatusList data) {
        this.data = data;
    }

    public CouponStatusList getData() {
        return data;
    }
}
