package com.zantong.mobilecttx.base.bean;

import com.google.gson.annotations.SerializedName;
import com.zantong.mobilecttx.user.bean.CouponBean;

import java.util.List;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhoujie on 2017/2/16.
 */

public class CouponResponse extends BaseResponse {
    @SerializedName("data")
    private List<CouponBean> couponList;

    public List<CouponBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponBean> couponList) {
        this.couponList = couponList;
    }
}
