package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.chongzhi.bean.RechargeCouponResult;
import com.zantong.mobile.chongzhi.bean.RechargeResult;
import com.zantong.mobile.chongzhi.dto.RechargeDTO;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

/**
 * Created by jianghw on 2017/4/26.
 * Description: 加油充值p
 * Update by:
 * Update day:
 */

public interface IRechargeAtyContract {

    interface IRechargeAtyView extends IMvpView<IRechargeAtyPresenter> {
        /**
         * 加载提示框
         */
        void showLoadingDialog();

        void dismissLoadingDialog();

        /**
         * 获取指定类型优惠券响应
         */
        void onCouponByTypeError(String message);

        void onCouponByTypeSucceed(RechargeCouponResult result);

        /**
         * 10.创建加油订单
         */
        void addOilCreateOrderError(String s);

        void addOilCreateOrderSucceed(RechargeResult result);

        RechargeDTO initRechargeDTO();

        /**
         * 54.充值接口
         */
        void onPayOrderByCouponSucceed(PayOrderResult result);

        void onPayOrderByCouponError(String s);
    }

    interface IRechargeAtyPresenter extends IMvpPresenter {
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
