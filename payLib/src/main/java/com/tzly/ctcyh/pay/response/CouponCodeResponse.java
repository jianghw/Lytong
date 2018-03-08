package com.tzly.ctcyh.pay.response;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponCodeResponse extends BaseResponse {

    private List<CouponCodeBean> data;

    public void setData(List<CouponCodeBean> data) {
        this.data = data;
    }

    public List<CouponCodeBean> getData() {
        return data;
    }
}
