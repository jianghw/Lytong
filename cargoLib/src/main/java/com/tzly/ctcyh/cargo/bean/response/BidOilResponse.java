package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.cargo.bean.BaseResponse;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class BidOilResponse extends BaseResponse {

    private List<BidOilBean> data;

    public void setData(List<BidOilBean> data) {
        this.data = data;
    }

    public List<BidOilBean> getData() {
        return data;
    }
}
