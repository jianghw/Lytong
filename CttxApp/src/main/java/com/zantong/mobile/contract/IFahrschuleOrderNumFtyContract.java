package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

/**
 * 报名支付页面
 */

public interface IFahrschuleOrderNumFtyContract {

    interface IFahrschuleOrderNumFtyView extends IMvpView<IFahrschuleOrderNumFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void onPayOrderByCouponError(String message);

        void onPayOrderByCouponSucceed(PayOrderResult result);
    }

    interface IFahrschuleOrderNumFtyPresenter extends IMvpPresenter {

        void onPayOrderByCoupon(String orderId, String orderPrice, String payType);

        void getBankPayHtml(String orderId, String orderPrice);
    }

}
