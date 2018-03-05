package com.zantong.mobilecttx.order.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 优惠券
 */

public class CouponFragmentResponse extends BaseResponse {

    private CouponFragmentLBean data;

    public void setData(CouponFragmentLBean data) {
        this.data = data;
    }

    public CouponFragmentLBean getData() {
        return data;
    }
}
