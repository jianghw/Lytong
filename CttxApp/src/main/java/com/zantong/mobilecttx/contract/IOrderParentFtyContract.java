package com.zantong.mobilecttx.contract;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.order.bean.OrderListBean;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.util.List;

import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 订单页面
 */

public interface IOrderParentFtyContract {

    interface IOrderParentFtyView extends IBaseView<IOrderParentFtyPresenter> {

        void getOrderListError(String message);

        void dataDistribution(String message, int orderStatus);

        void nonPaymentData(List<OrderListBean> orderList, int page);

        void havePaymentData(List<OrderListBean> orderList, int page);

        void cancelPaymentData(List<OrderListBean> orderList, int page);

        void allPaymentData(List<OrderListBean> data, int pager);

        void updateOrderStatusError(String message);

        void updateOrderStatusSucceed(BaseResponse result);

        void onPayOrderByCouponError(String message);

        void onPayOrderByCouponSucceed(PayOrderResponse result);

        void getBankPayHtmlSucceed(PayOrderResponse result, String orderId);

        void toastError(String message);
    }

    interface IOrderParentFtyPresenter extends IBasePresenter {
        void getOrderList(int pager);

        String initUserId();

        void updateOrderStatus(OrderListBean bean);

        void onPayOrderByCoupon(String orderId, String orderPrice, String payType);

        void getBankPayHtml(String orderId, String orderPrice);

        void cancelOrder(OrderListBean bean);

        void aliPayHtml(String orderId);
    }
}
