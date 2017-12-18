package com.tzly.ctcyh.pay.bean.response;

import com.tzly.ctcyh.pay.bean.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CodeDetailResponse extends BaseResponse {

    private CouponDetailBean data;

    public void setData(CouponDetailBean data) {
        this.data = data;
    }

    public CouponDetailBean getData() {
        return data;
    }
}
