package com.zantong.mobile.base.bean;

import com.google.gson.annotations.SerializedName;
import com.zantong.mobile.user.bean.CouponBean;

import java.util.List;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhoujie on 2017/2/16.
 */

public class CouponResult extends BaseResult {
    @SerializedName("data")
    private List<CouponBean> couponList;

    public List<CouponBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponBean> couponList) {
        this.couponList = couponList;
    }
}
