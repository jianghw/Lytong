package com.zantong.mobile.chongzhi.bean;

import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class RechargeCouponResult extends BaseResult {

    private List<RechargeCouponBean> data;

    public void setData(List<RechargeCouponBean> data) {
        this.data = data;
    }

    public List<RechargeCouponBean> getData() {
        return data;
    }

    public RechargeCouponResult() {
    }

}
