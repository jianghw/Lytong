package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * 3.获取商户区域列表
 */
public class MerchantAresResult extends BaseResult {

    private List<MerchantAresBean> data;

    public void setData(List<MerchantAresBean> data) {
        this.data = data;
    }

    public List<MerchantAresBean> getData() {
        return data;
    }
}
