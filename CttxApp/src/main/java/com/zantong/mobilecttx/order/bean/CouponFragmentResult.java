package com.zantong.mobilecttx.order.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * 优惠券
 */

public class CouponFragmentResult extends BaseResult {

    private CouponFragmentLBean data;

    public void setData(CouponFragmentLBean data) {
        this.data = data;
    }

    public CouponFragmentLBean getData() {
        return data;
    }
}
