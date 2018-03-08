package com.tzly.ctcyh.pay.response;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class CouponResponse extends BaseResponse {

    private List<CouponBean> data;

    public void setData(List<CouponBean> data) {
        this.data = data;
    }

    public List<CouponBean> getData() {
        return data;
    }
}
