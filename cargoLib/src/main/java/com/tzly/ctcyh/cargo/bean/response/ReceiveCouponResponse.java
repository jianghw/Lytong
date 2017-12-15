package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.cargo.bean.BaseResponse;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class ReceiveCouponResponse extends BaseResponse {

    private List<RefuelOilBean> data;

    public void setData(List<RefuelOilBean> data) {
        this.data = data;
    }

    public List<RefuelOilBean> getData() {
        return data;
    }
}