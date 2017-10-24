package com.tzly.ctcyh.pay.data_m;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRemoteSource {
    /**
     * 57.获取指定类型优惠券
     */
    Observable<CouponResponse> getCouponByType(String userId, String extraType);
}
