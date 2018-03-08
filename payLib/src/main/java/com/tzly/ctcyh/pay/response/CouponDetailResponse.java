package com.tzly.ctcyh.pay.response;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponDetailResponse extends BaseResponse {

    private CouponDetailBean data;

    public void setData(CouponDetailBean data) {
        this.data = data;
    }

    public CouponDetailBean getData() {
        return data;
    }
}
