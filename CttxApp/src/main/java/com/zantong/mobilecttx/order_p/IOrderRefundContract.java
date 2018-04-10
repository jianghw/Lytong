package com.zantong.mobilecttx.order_p;

import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * 修改订单信息
 */

public interface IOrderRefundContract {

    interface IOrderRefundView extends IBaseView<IOrderRefundPresenter> {
        String getChannel();

        String getOrderId();

        String getRemark();

        void infoError(String message);

        void infoSucceed(OrderRefundResponse result);
    }

    interface IOrderRefundPresenter extends IBasePresenter {
        void info();
    }

}
