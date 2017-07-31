package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;

/**
 * 报名支付页面
 */

public interface IOrderDetailContract {

    interface IOrderDetailView extends IMvpView<IOrderDetailPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getOrderDetailError(String message);

        void getOrderDetailSucceed(OrderDetailResult result);

        String getOrderId();
    }

    interface IOrderDetailPresenter extends IMvpPresenter {

        void getOrderDetail();

        String getOrderId();

    }

}
