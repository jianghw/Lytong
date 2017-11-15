package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.util.List;

/**
 * 3.获取商户区域列表
 */
public class MerchantAresResponse extends BaseResponse {

    private List<MerchantAresBean> data;

    public void setData(List<MerchantAresBean> data) {
        this.data = data;
    }

    public List<MerchantAresBean> getData() {
        return data;
    }
}
