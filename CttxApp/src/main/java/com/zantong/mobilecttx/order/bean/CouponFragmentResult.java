package com.zantong.mobilecttx.order.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

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
