package com.zantong.mobilecttx.order_p;

import com.tzly.ctcyh.java.response.order.OrderInfoResponse;
import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.order.bean.OrderDetailResponse;

/**
 * 报名支付页面
 */

public interface IOrderDetailContract {

    interface IOrderDetailView extends IBaseView<IOrderDetailPresenter> {

        void getOrderDetailError(String message);

        void getOrderDetailSucceed(OrderDetailResponse result);

        String getOrderId();

        String getChannel();

        void infoError(String message);

        void infoSucceed(OrderRefundResponse result);

        void UserOrderInfoError(String message);

        void UserOrderInfoSucceed(OrderInfoResponse result);
    }

    interface IOrderDetailPresenter extends IBasePresenter {
        void getOrderDetail();

        String getOrderId();

        void info();

        void getUserOrderInfo();
    }

}
