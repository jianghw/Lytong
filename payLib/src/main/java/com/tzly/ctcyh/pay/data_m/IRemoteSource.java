package com.tzly.ctcyh.pay.data_m;

import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CouponDetailResponse;
import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.bean.response.CouponStatusResponse;
import com.tzly.ctcyh.pay.bean.response.OrderDetailResponse;
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
    Observable<CouponResponse> getCouponByType(String userId, String extraType, int payType);

    /**
     * 31.创建订单后获取订单信息
     */
    Observable<PayTypeResponse> getOrderInfo(String extraOrderId);

    /**
     * 5.获取工行支付页面
     */
    Observable<PayUrlResponse> getBankPayHtml(String extraOrderId, String amount, int couponUserId);

    /**
     * 9.获取订单详情
     */
    Observable<OrderDetailResponse> getOrderDetail(String orderId);

    /**
     * 获取优惠券列表
     */
    Observable<CouponStatusResponse> couponUserList(String rasUserID, String couponStatus);

    /**
     * 2.4.27删除用户优惠券
     */
    Observable<BaseResponse> delUsrCoupon(String rasUserID, String couponId);

    /**
     * 优惠券详情
     */
    Observable<CouponDetailResponse> couponDetail(String couponId);
}
