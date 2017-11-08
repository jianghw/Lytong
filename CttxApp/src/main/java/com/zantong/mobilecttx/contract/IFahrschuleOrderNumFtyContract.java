package com.zantong.mobilecttx.contract;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

/**
 * 报名支付页面
 */

public interface IFahrschuleOrderNumFtyContract {

    interface IFahrschuleOrderNumFtyView extends IBaseView<IFahrschuleOrderNumFtyPresenter> {

        void onPayOrderByCouponError(String message);

        void onPayOrderByCouponSucceed(PayOrderResponse result);
    }

    interface IFahrschuleOrderNumFtyPresenter extends IBasePresenter {

        void onPayOrderByCoupon(String orderId, String orderPrice, String payType);

        void getBankPayHtml(String orderId, String orderPrice);
    }

}
