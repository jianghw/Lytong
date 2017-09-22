package com.zantong.mobile.contract.browser;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.order.bean.OrderDetailResult;

/**
 * 报名支付页面
 */

public interface IPayBrowserFtyContract {

    interface IPayBrowserFtyView extends IMvpView<IPayBrowserFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getOrderDetailError(String message);

        void getOrderDetailSucceed(OrderDetailResult result);

        void intervalOrderDetailError(String message);

        void orderDetailCompleted();

        String getOrderId();
    }

    interface IPayBrowserFtyPresenter extends IMvpPresenter {

        void getOrderDetail();

        String getOrderId();

        void orderDetail();
    }

}
