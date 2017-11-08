package com.zantong.mobilecttx.oiling_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.chongzhi.bean.RechargeResponse;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

/**
 * Created by jianghw on 2017/4/26.
 * Description: 加油充值p
 * Update by:
 * Update day:
 */

public interface IRechargeAtyContract {

    interface IRechargeAtyView extends IBaseView<IRechargeAtyPresenter> {

        /**
         * 获取指定类型优惠券响应
         */
        void onCouponByTypeError(String message);

        void onCouponByTypeSucceed(RechargeCouponResponse result);

        /**
         * 10.创建加油订单
         */
        void addOilCreateOrderError(String s);

        void addOilCreateOrderSucceed(RechargeResponse result);

        RechargeDTO initRechargeDTO();

        /**
         * 54.充值接口
         */
        void onPayOrderByCouponSucceed(PayOrderResponse result);

        void onPayOrderByCouponError(String s);
    }

    interface IRechargeAtyPresenter extends IBasePresenter {
        /**
         * 57.获取指定类型优惠券
         */
        void getCouponByType();

        /**
         * UserId
         */
        String initUserId();

        /**
         * 10.创建加油订单
         */
        void addOilCreateOrder();

        RechargeDTO initRechargeDTO();

        /**
         * 54.充值接口
         *
         * @param orderId
         * @param orderPrice
         * @param payType
         */
        void onPayOrderByCoupon(String orderId, String orderPrice, String payType);
    }

}
