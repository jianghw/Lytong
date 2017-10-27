package com.tzly.ctcyh.pay.data_m;

import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.PayTypeResponse;
import com.tzly.ctcyh.pay.bean.response.PayUrlResponse;

import rx.Observable;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IRemoteSource {
    /**
     * 57.获取指定类型优惠券
     */
    Observable<CouponResponse> getCouponByType(String userId, String extraType);

    /**
     * 31.创建订单后获取订单信息
     */
    Observable<PayTypeResponse> getOrderInfo(String extraOrderId);
    /**
     * 5.获取工行支付页面
     */
    Observable<PayUrlResponse> getBankPayHtml(String extraOrderId, String amount, int couponUserId);
}
