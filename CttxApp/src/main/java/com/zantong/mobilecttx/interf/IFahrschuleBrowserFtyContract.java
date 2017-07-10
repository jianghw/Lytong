package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;

/**
 * 报名支付页面
 */

public interface IFahrschuleBrowserFtyContract {

    interface IFahrschuleBrowserFtyView extends IMvpView<IFahrschuleBrowserFtyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getOrderDetailError(String message);

        void getOrderDetailSucceed(OrderDetailResult result);

        void intervalOrderDetailError(String message);

        void orderDetailCompleted();

        String getOrderId();
    }

    interface IFahrschuleBrowserFtyPresenter extends IMvpPresenter {

        void getOrderDetail();

        String getOrderId();

        void orderDetail();
    }

}
