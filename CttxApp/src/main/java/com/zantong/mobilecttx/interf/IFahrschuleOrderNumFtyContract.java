package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;

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
