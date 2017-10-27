package com.tzly.ctcyh.pay.bean.response;

import com.tzly.ctcyh.pay.bean.BaseResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class PayTypeResponse extends BaseResponse {

    private PayTypeBean data;

    public void setData(PayTypeBean data) {
        this.data = data;
    }

    public PayTypeBean getData() {
        return data;
    }
}
