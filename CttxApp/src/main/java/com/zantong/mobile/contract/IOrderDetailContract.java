package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.order.bean.OrderDetailResult;

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
