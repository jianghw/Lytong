package com.tzly.annual.base.bean.response;

import com.tzly.annual.base.bean.BaseResponse;

import java.util.List;

/**
 * 2.获取订单列表
 */
public class CattleOrderResponse extends BaseResponse {

    private List<CattleOrderBean> data;

    public void setData(List<CattleOrderBean> data) {
        this.data = data;
    }

    public List<CattleOrderBean> getData() {
        return data;
    }
}
