package com.tzly.ctcyh.pay.data_m;

import com.tzly.ctcyh.java.request.card.ApplyCTCardDTO;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.tzly.ctcyh.pay.response.CodeDetailResponse;
import com.tzly.ctcyh.pay.response.CouponCodeResponse;
import com.tzly.ctcyh.pay.response.CouponDetailResponse;
import com.tzly.ctcyh.pay.response.CouponResponse;
import com.tzly.ctcyh.pay.response.CouponStatusResponse;
import com.tzly.ctcyh.pay.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.response.PayTypeResponse;
import com.tzly.ctcyh.pay.response.PayUrlResponse;
import com.tzly.ctcyh.pay.response.PayWeixinResponse;

import java.util.List;

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

    /**
     * 微信支付
     */
    Observable<PayWeixinResponse> weChatPay(String orderId, String amount, int couponUserId);

    /**
     * 码券列表
     */
    Observable<CouponCodeResponse> getCodeList(String rasUserID, String couponStatus);

    /**
     * 删除码券
     */
    Observable<BaseResponse> deleteCode(String codeId, String rasUserID);

    /**
     * .码券详情
     */
    Observable<CodeDetailResponse> getCodeDetail(String codeId);

    Observable<ViolationNumBean> bank_v003_01(String msg);

    Observable<BaseResponse> updateState(List<ViolationNum> json);

    /**
     * 分享人优惠券信息
     */
    Observable<CouponInfoResponse> getCouponInfo(String orderId);

    /**
     * 分享人信息统计
     */
    Observable<BaseResponse> shareUser(String userID, String goodsType, String statu);

    /**
     * 提交银行数据
     */
    Observable<BaseResponse> applyRecord(ApplyCTCardDTO applyCTCardDTO);
}
